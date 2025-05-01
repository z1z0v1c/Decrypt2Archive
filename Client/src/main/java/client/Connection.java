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

    public Connection(String address, int port) throws IllegalArgumentException, IOException {
        socket = new Socket(address, port);
        output = new DataOutputStream(socket.getOutputStream());
        input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
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
