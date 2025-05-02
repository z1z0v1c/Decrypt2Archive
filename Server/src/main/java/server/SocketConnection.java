package server;

import java.net.*;
import java.io.*;

public class SocketConnection {
    private Socket socket;
    private ServerSocket serverSocket;
    private DataInputStream input;
    private DataOutputStream output;

    public SocketConnection(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        System.out.println("Server started");
        System.out.println("Waiting for a client ...");
    }

    public void acceptClient() throws IOException {
        socket = serverSocket.accept();
        socket.setKeepAlive(true);

        input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        output = new DataOutputStream(socket.getOutputStream());

        System.out.println("Client accepted");
    }

    public String getInputDirectory() throws IOException {
        String pathToInputDir = input.readUTF();

        System.out.println(pathToInputDir);

        return pathToInputDir;
    }

    public void sendResponse(String message) throws IOException {
        output.writeUTF(message);
    }

    public void disconnect() {
        System.out.println("Closing connection");

        // Close the connection
        try {
            socket.close();
            serverSocket.close();
            input.close();
            output.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}
