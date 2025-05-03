package application;

import database.DatabaseConnection;
import file.FileProcessor;
import network.SocketConnection;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

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

            String inputDirectory = socketConnection.getInputDirectory();
            String key = databaseConnection.selectKey(inputDirectory);

            fileProcessor = new FileProcessor(inputDirectory, pathToOutputDir);

            fileProcessor.process(inputDirectory, pathToOutputDir, key);

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
