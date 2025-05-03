package server;

import database.DatabaseConnection;
import encryption.Encryptor;
import fileprocessor.FileProcessor;
import fileprocessor.FileProcessorFactory;
import picocli.CommandLine.Option;
import util.ZipDirectory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Aleksandar Zizovic
 */
public class Server implements Runnable {
    @Option(names = {"-p", "--port-number"}, defaultValue = "8888", description = "Server port number")
    private static int portNumber;

    @Option(names = {"-d", "--database"}, defaultValue = "database.sqlite3", description = "Database location")
    private static String pathToDatabase;

    @Option(names = {"-o", "--output-directory"}, required = true, description = "Path to the output directory")
    private static String pathToOutputDir;

    private SocketConnection socketConnection;
    private DatabaseConnection databaseConnection;

    @Override
    public void run() {
        try {
            createDatabaseConnection();
            createSocketConnection();

            acceptClient();

            String inputDirectory = socketConnection.getInputDirectory();
            String key = databaseConnection.selectKey(inputDirectory);

            List<String> text = readFile(inputDirectory);

            List<String> decryptedText = decrypt(text, key);

            writeText(decryptedText, pathToOutputDir);

            ZipDirectory.zipDirectory(pathToOutputDir);


            socketConnection.sendResponse("Success");
            databaseConnection.logToDatabase("Success", new Date(System.currentTimeMillis()).toString());

            socketConnection.disconnect();

            databaseConnection.logToDatabase("Closing connection", new Date(System.currentTimeMillis()).toString());
            databaseConnection.close();


        } catch (IOException ex) {
            try {
                socketConnection.sendResponse(ex.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            databaseConnection.logToDatabase("IOException", new Date(System.currentTimeMillis()).toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createDatabaseConnection() {
        databaseConnection = new DatabaseConnection(pathToDatabase);
    }

    public void createSocketConnection() {
        databaseConnection.logToDatabase("Starting the server...", new Date(System.currentTimeMillis()).toString());

        try {
            socketConnection = new SocketConnection(portNumber);
        } catch (IOException e) {
            databaseConnection.logToDatabase("IOException", new Date(System.currentTimeMillis()).toString());
        }

        databaseConnection.logToDatabase("Server started", new Date(System.currentTimeMillis()).toString());
    }

    public void acceptClient() {
        try {
            socketConnection.acceptClient();
        } catch (IOException e) {
            databaseConnection.logToDatabase("IOException", new Date(System.currentTimeMillis()).toString());
        }

        databaseConnection.logToDatabase("Client accepted", new Date(System.currentTimeMillis()).toString());
    }

    public List<String> readFile(String pathToInputDir) throws IOException {
        File file = new File(pathToOutputDir);

        if (!file.exists()) {
            boolean isCreated = file.getParentFile().mkdirs();

            if (!isCreated) {
                throw new IOException("Output directory couldn't be created!");
            }
        }

        FileProcessor fileProcessor = FileProcessorFactory.getFileProcessor(pathToInputDir);

        System.out.println("Reading file: " + pathToInputDir);
        databaseConnection.logToDatabase("Reading file: " + pathToInputDir, new Date(System.currentTimeMillis()).toString());

        List<String> text = fileProcessor.getTextFromFile(pathToInputDir);

        System.out.println("File is read: " + pathToInputDir);
        databaseConnection.logToDatabase("File is read: " + pathToInputDir, new Date(System.currentTimeMillis()).toString());

        return text;
    }

    public List<String> decrypt(List<String> text, String key) {
        List<String> decryptedText = new ArrayList<>();

        for (String word : text) {
            String decryptedTXT = Encryptor.decrypt(key, word);
            decryptedText.add(decryptedTXT);
        }

        return decryptedText;
    }

    public void writeText(List<String> text, String outputDirectory) {
        try {
            FileProcessor fileProcessor = FileProcessorFactory.getFileProcessor(outputDirectory);

            fileProcessor.printToFile(pathToOutputDir, text);
        } catch (IOException e) {
            databaseConnection.logToDatabase("IOException", new Date(System.currentTimeMillis()).toString());
        }
        System.out.println("Decrypted file path: " + pathToOutputDir);
        databaseConnection.logToDatabase("Decrypted file path: " + pathToOutputDir, new Date(System.currentTimeMillis()).toString());
    }
}
