package server;

import databases.Database;
import databases.impl.SqliteDatabase;
import files.FileProcessor;
import logger.CompositeLogger;
import network.SocketConnection;
import org.apache.logging.log4j.Level;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/// @author Aleksandar Zizovic
public class Server implements Closeable {
    private CompositeLogger logger;
    private Database database;
    private SocketConnection socketConnection;
    private FileProcessor fileProcessor;

    public void connectToDatabase(String databasePath) throws SQLException {
        database = new SqliteDatabase(databasePath);
        logger = new CompositeLogger(Server.class, database);

        logger.log(Level.INFO, "Connected to the database.");
    }

    public void serve(int port) throws IOException, SQLException {
        logger.log(Level.INFO, "Starting the server...");

        socketConnection = new SocketConnection(port);

        logger.log(Level.INFO, "Server started");

        socketConnection.acceptClient();

        logger.log(Level.INFO, "Client accepted");

    }

    public void processRequest(String outputFile) throws IOException, SQLException {
        logger.log(Level.INFO, "Processing the request...");

        String inputFile = socketConnection.getData();

        logger.log(Level.INFO, String.format("Input file path: %s", inputFile));

        String fileName = new File(inputFile).getName();

        String key = database.selectKey(fileName);

        logger.log(Level.INFO, String.format("Key for the given file: %s", inputFile));

        fileProcessor = new FileProcessor(inputFile, outputFile);

        logger.log(Level.INFO, "Decrypting the file...");

        fileProcessor.process(inputFile, outputFile, key);

        logger.log(Level.INFO, "Success");

        socketConnection.sendResponse("Success");

        logger.log(Level.INFO, "Shutting down the server...");
    }

    @Override
    public void close() throws IOException {
        try {
            logger.log(Level.INFO, "Closing database connection...");
        } catch (SQLException e) {
            throw new IOException(e);
        }

        socketConnection.close();
        database.close();
    }
}
