package client;

import java.net.*;
import java.io.*;

/**
 * @author Aleksandar Zizovic
 */
public class Connection {
    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;

    public Connection(String address, int port) throws IOException {
        try {
            // Establish a connection
            this.socket = new Socket(address, port);
            // Get data streams
            this.output = new DataOutputStream(socket.getOutputStream());
            this.input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IllegalArgumentException | UnknownHostException ex) {
            throw new IllegalArgumentException("Illegal argument: " + ex.getMessage());
        }
    }

    public String sendRequest(String data) throws IOException {
        this.output.writeUTF(data);

        return this.input.readUTF();
    }

    public void disconnect() throws IOException {
        this.input.close();
        this.output.close();
        this.socket.close();
    }
}
