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

    public Server(int portNumber, String databasePath) throws SQLException, IOException {
        database = new SqliteDatabase(databasePath);
        socketConnection = new SocketConnection(portNumber);
    }

    public void serve(String outputDirectory) throws IOException, SQLException {
        database.log("Starting the server...", new Date(System.currentTimeMillis()).toString());

        socketConnection.acceptClient();

        database.log("Server started", new Date(System.currentTimeMillis()).toString());
        database.log("Client accepted", new Date(System.currentTimeMillis()).toString());

        String inputFile = socketConnection.getData();
        String fileName = new File(inputFile).getName();
        String key = database.selectKey(fileName);

        fileProcessor = new FileProcessor(inputFile, outputDirectory);

        fileProcessor.process(inputFile, outputDirectory, key);

        socketConnection.sendResponse("Success");

        database.log("Success", new Date(System.currentTimeMillis()).toString());

        database.log("Closing connection", new Date(System.currentTimeMillis()).toString());
    }

    @Override
    public void close() throws IOException {
        socketConnection.close();
        database.close();
    }
}
