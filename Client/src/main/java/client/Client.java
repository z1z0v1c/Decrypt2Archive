package client;

import java.net.*;
import java.io.*;

/**
 * @author Aleksandar Zizovic
 */
public class Client {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public void connect(String address, int port) throws IOException {
        try {
            // Establish a connection
            socket = new Socket(address, port);

            // Sends request data
            output = new DataOutputStream(socket.getOutputStream());

            // Gets response message
            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IllegalArgumentException | UnknownHostException ex) {
            throw new IllegalArgumentException("Illegal argument: " + ex.getMessage());
        }
    }

    public String sendRequest(String data) throws IOException {
        output.writeUTF(data);

        return input.readUTF();
    }

    public void disconnect() throws IOException {
        input.close();
        output.close();
        socket.close();
    }
}
