package application;

import databases.Database;
import databases.impl.SqliteDatabase;
import files.FileProcessor;
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
    private static String outputDirectory;

    private Database database;

    private SocketConnection socketConnection;

    private FileProcessor fileProcessor;

    @Override
    public void run() {
        try {
            database = new SqliteDatabase(pathToDatabase);

            database.log("Starting the server...", new Date(System.currentTimeMillis()).toString());

            socketConnection = new SocketConnection(portNumber);

            database.log("Server started", new Date(System.currentTimeMillis()).toString());

            socketConnection.acceptClient();

            database.log("Client accepted", new Date(System.currentTimeMillis()).toString());

            String inputDirectory = socketConnection.getData();
            String key = database.selectKey(inputDirectory);

            fileProcessor = new FileProcessor(inputDirectory, outputDirectory);

            fileProcessor.process(inputDirectory, outputDirectory, key);

            socketConnection.sendResponse("Success");

            database.log("Success", new Date(System.currentTimeMillis()).toString());

            socketConnection.close();

            database.log("Closing connection", new Date(System.currentTimeMillis()).toString());

            database.close();
        } catch (IOException ex) {
            try {
                socketConnection.sendResponse(ex.getMessage());
                database.log("IOException", new Date(System.currentTimeMillis()).toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
