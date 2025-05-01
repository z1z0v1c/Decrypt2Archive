package server;

import database.DBConnection;
import encryption.Encryptor;

import java.net.*;
import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.ZipDirectory;
import fileprocessor.FileProcessor;
import fileprocessor.FileProcessorFactory;

public class Connection {
    private Socket socket;
    private ServerSocket serverSocket;
    private DataInputStream input;
    private DataOutputStream output;
    private final DBConnection dbConnection;

    public Connection(String pathToDatabase) {
        dbConnection = new DBConnection(pathToDatabase);
    }

    public void start(int port, String pathToOutputDir) throws IOException {
        String pathToInputDir = "";

        try {
            serverSocket = new ServerSocket(port);

            System.out.println("Server started");
            dbConnection.logToDatabase("Server started", new Date(System.currentTimeMillis()).toString());
            System.out.println("Waiting for a client ...");

            socket = serverSocket.accept();

            System.out.println("Client accepted");
            dbConnection.logToDatabase("Client accepted", new Date(System.currentTimeMillis()).toString());

            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());

            pathToInputDir = input.readUTF();

            System.out.println(pathToInputDir);
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            fileDecryption(pathToInputDir, pathToOutputDir);

            ZipDirectory.zipDirectory(pathToOutputDir);

            output.writeUTF("Success");
        } catch (IOException ex) {
            output.writeUTF(ex.getMessage());
            dbConnection.logToDatabase("IOException", new Date(System.currentTimeMillis()).toString());
        }

        output.writeUTF("Success");
        dbConnection.logToDatabase("Success", new Date(System.currentTimeMillis()).toString());

        disconnect();
    }

    public void fileDecryption(String pathToInputDir, String pathToOutputDir) throws IOException {
        File file = new File(pathToOutputDir);

        if (!file.exists()) {
            boolean isCreated = file.getParentFile().mkdirs();

            if (!isCreated) {
                throw new IOException("Output directory couldn't be created!");
            }
        }

        FileProcessor txtProcessor = FileProcessorFactory.getFileProcessor(pathToInputDir);

        String keyTXT = dbConnection.selectKey(pathToInputDir);

        System.out.println("Reading file: " + pathToInputDir);
        dbConnection.logToDatabase("Reading file: " + pathToInputDir, new Date(System.currentTimeMillis()).toString());

        List<String> listTXT = txtProcessor.getTextFromFile(pathToInputDir);

        System.out.println("File is read: " + pathToOutputDir);
        dbConnection.logToDatabase("File is read: " + pathToOutputDir, new Date(System.currentTimeMillis()).toString());

        List<String> listTXTDecrypted = new ArrayList<>();

        System.out.println("Decrypting file: " + pathToOutputDir);
        dbConnection.logToDatabase("Decrypting file: " + pathToOutputDir, new Date(System.currentTimeMillis()).toString());

        for (String value : listTXT) {
            String decryptedTXT = Encryptor.decrypt(keyTXT, value);
            listTXTDecrypted.add(decryptedTXT);
        }

        System.out.println("File is decrypted: " + pathToInputDir);
        dbConnection.logToDatabase("File is decrypted: " + pathToInputDir, new Date(System.currentTimeMillis()).toString());

        txtProcessor.printToFile(pathToOutputDir, listTXTDecrypted);

        System.out.println("Decrypted file path: " + pathToOutputDir);
        dbConnection.logToDatabase("Decrypted file path: " + pathToOutputDir, new Date(System.currentTimeMillis()).toString());
    }

    public void disconnect() {
        System.out.println("Closing connection");

        dbConnection.logToDatabase("Closing connection", LocalDateTime.now().toString());

        // Close the connection
        try {
            socket.close();
            serverSocket.close();
            input.close();
            output.close();
            dbConnection.close();
        } catch (IOException i) {
            System.out.println(i);
        } catch (SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
