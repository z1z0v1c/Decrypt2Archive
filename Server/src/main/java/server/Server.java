package server;

import database.DBConnection;
import encryption.Encryptor;
import java.net.*;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.ZipDirectory;
import fileprocessor.FileProcessor;
import fileprocessor.FileProcessorFacotry;

public class Server {

    private Socket socket;
    private ServerSocket serverSocket;
    private DataInputStream input;
    private DataOutputStream out;
    private final DBConnection dbConnection;

    public Server(String pathToDatabase) {
        dbConnection = new DBConnection(pathToDatabase);
    }

    public void start(int port, String pathToOutputDir) throws IOException, Exception {
        String pathToInputDir = "";
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            dbConnection.logToDatabase("Server started", new Date(System.currentTimeMillis()).toString());

            System.out.println("Waiting for a client ...");

            socket = serverSocket.accept();
            System.out.println("Client accepted");
            dbConnection.logToDatabase("Client accepted", new Date(System.currentTimeMillis()).toString());

            input = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());

            pathToInputDir = input.readUTF();
            System.out.println(pathToInputDir);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        fileDecryption(pathToInputDir, pathToOutputDir);
        ZipDirectory.zipDirectory(pathToOutputDir);
        out.writeUTF("Success");
        dbConnection.logToDatabase("Success", new Date(System.currentTimeMillis()).toString());
        disconnect();
    }

    public void fileDecryption(String pathToInputDir, String pathToOutputDir) throws Exception {
        File file = new File(pathToOutputDir);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        FileProcessor txtProcessor = FileProcessorFacotry.getFileProcessor(pathToInputDir);
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
        dbConnection.logToDatabase("Closing connection", new Date(System.currentTimeMillis()).toString());
        try {
            // close connection
            socket.close();
            input.close();
            out.close();
            dbConnection.close();
        } catch (IOException i) {
            System.out.println(i);
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
