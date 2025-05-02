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
            databaseConnection = new DatabaseConnection(pathToDatabase);
            socketConnection = new SocketConnection(portNumber);
            databaseConnection.logToDatabase("Server started", new Date(System.currentTimeMillis()).toString());

            socketConnection.acceptClient();
            databaseConnection.logToDatabase("Client accepted", new Date(System.currentTimeMillis()).toString());

            String inputDirectory = socketConnection.getInputDirectory();
            String key = databaseConnection.selectKey(inputDirectory);

            decryptFile(inputDirectory, pathToOutputDir, key);

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

    public void decryptFile(String pathToInputDir, String pathToOutputDir, String keyTXT) throws IOException {
        File file = new File(pathToOutputDir);

        if (!file.exists()) {
            boolean isCreated = file.getParentFile().mkdirs();

            if (!isCreated) {
                throw new IOException("Output directory couldn't be created!");
            }
        }

        FileProcessor txtProcessor = FileProcessorFactory.getFileProcessor(pathToInputDir);

        System.out.println("Reading file: " + pathToInputDir);
        databaseConnection.logToDatabase("Reading file: " + pathToInputDir, new Date(System.currentTimeMillis()).toString());

        List<String> listTXT = txtProcessor.getTextFromFile(pathToInputDir);

        System.out.println("File is read: " + pathToOutputDir);
        databaseConnection.logToDatabase("File is read: " + pathToOutputDir, new Date(System.currentTimeMillis()).toString());

        List<String> listTXTDecrypted = new ArrayList<>();

        System.out.println("Decrypting file: " + pathToOutputDir);
        databaseConnection.logToDatabase("Decrypting file: " + pathToOutputDir, new Date(System.currentTimeMillis()).toString());

        for (String value : listTXT) {
            String decryptedTXT = Encryptor.decrypt(keyTXT, value);
            listTXTDecrypted.add(decryptedTXT);
        }

        System.out.println("File is decrypted: " + pathToInputDir);
        databaseConnection.logToDatabase("File is decrypted: " + pathToInputDir, new Date(System.currentTimeMillis()).toString());

        txtProcessor.printToFile(pathToOutputDir, listTXTDecrypted);

        System.out.println("Decrypted file path: " + pathToOutputDir);
        databaseConnection.logToDatabase("Decrypted file path: " + pathToOutputDir, new Date(System.currentTimeMillis()).toString());
    }
}
