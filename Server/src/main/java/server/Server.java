package server;

import databases.Database;
import databases.impl.SqliteDatabase;
import files.FileProcessor;
import network.SocketConnection;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

/// @author Aleksandar Zizovic
public class Server implements Closeable {
    private Database database;
    private SocketConnection socketConnection;
    private FileProcessor fileProcessor;

    public void connectToDatabase(String databasePath) throws SQLException {
        database = new SqliteDatabase(databasePath);
    }

    public void serve(int port) throws IOException, SQLException {
        database.log("Starting the server...", new Date(System.currentTimeMillis()).toString());

        socketConnection = new SocketConnection(port);

        socketConnection.acceptClient();

        database.log("Server started", new Date(System.currentTimeMillis()).toString());
        database.log("Client accepted", new Date(System.currentTimeMillis()).toString());

        database.log("Closing connection", new Date(System.currentTimeMillis()).toString());
    }

    public void processRequest(String outputFile) throws IOException, SQLException {
        String inputFile = socketConnection.getData();

        String fileName = new File(inputFile).getName();

        String key = database.selectKey(fileName);

        fileProcessor = new FileProcessor(inputFile, outputFile);

        fileProcessor.process(inputFile, outputFile, key);

        socketConnection.sendResponse("Success");

        database.log("Success", new Date(System.currentTimeMillis()).toString());
    }

    @Override
    public void close() throws IOException {
        socketConnection.close();
        database.close();
    }
}
