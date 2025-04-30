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

    public void connect(String address, int port) {
        // Establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // Sends output to the socket
            output = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public String requestData(String data) {
        try {
            output.writeUTF(data);
            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            return input.readUTF();
        } catch (IOException i) {
            return i.getMessage();
        }
    }

    public void disconnect() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}
