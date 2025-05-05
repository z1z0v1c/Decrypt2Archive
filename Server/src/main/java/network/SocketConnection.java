package network;

import java.net.*;
import java.io.*;

/// @author Aleksandar Zizovic
public class SocketConnection implements Closeable {
    private Socket socket;
    private ServerSocket serverSocket;
    private DataInputStream input;
    private DataOutputStream output;

    public SocketConnection(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void acceptClient() throws IOException {
        socket = serverSocket.accept();
        socket.setKeepAlive(true);

        input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        output = new DataOutputStream(socket.getOutputStream());
    }

    public String getData() throws IOException {
        return input.readUTF();
    }

    public void sendResponse(String message) throws IOException {
        output.writeUTF(message);
    }

    @Override
    public void close() throws IOException {
        socket.close();
        serverSocket.close();
        input.close();
        output.close();
    }
}
