package application;

import database.DatabaseConnection;
import file.encryptor.FileEncryptor;
import file.processor.FileProcessor;
import file.processor.FileProcessorFactory;
import network.SocketConnection;
import picocli.CommandLine.Option;
import file.zipper.FileZipper;

import java.io.IOException;
import java.sql.SQLException;
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
    private FileZipper fileZipper;
    private FileEncryptor encryptor;
    private FileProcessor fileProcessor;

    @Override
    public void run() {
        try {
            databaseConnection = new DatabaseConnection(pathToDatabase);

            databaseConnection.log("Starting the server...", new Date(System.currentTimeMillis()).toString());

            socketConnection = new SocketConnection(portNumber);

            databaseConnection.log("Server started", new Date(System.currentTimeMillis()).toString());

            socketConnection.acceptClient();
            databaseConnection.log("Client accepted", new Date(System.currentTimeMillis()).toString());

            fileZipper = new FileZipper();
            encryptor = new FileEncryptor();

            String inputDirectory = socketConnection.getInputDirectory();
            String key = databaseConnection.selectKey(inputDirectory);

            fileProcessor = FileProcessorFactory.getFileProcessor(inputDirectory);

            List<String> text = fileProcessor.readText(inputDirectory);

            List<String> decryptedText = encryptor.decrypt(text, key);

            fileProcessor = FileProcessorFactory.getFileProcessor(pathToOutputDir);

            fileProcessor.writeText(pathToOutputDir, decryptedText);

            fileZipper.zipDirectory(pathToOutputDir);

            socketConnection.sendResponse("Success");
            databaseConnection.log("Success", new Date(System.currentTimeMillis()).toString());

            socketConnection.close();

            databaseConnection.log("Closing connection", new Date(System.currentTimeMillis()).toString());
            databaseConnection.close();
        } catch (IOException ex) {
            try {
                socketConnection.sendResponse(ex.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            databaseConnection.log("IOException", new Date(System.currentTimeMillis()).toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
