package client;

import network.SocketConnection;

import java.io.Closeable;
import java.io.IOException;

public class Client implements Closeable {
    private SocketConnection socketConnection;

    public void connect(String serverAddress, int portNumber) throws IOException {
        socketConnection = new SocketConnection(serverAddress, portNumber);
    }

    public String sendData(String data) throws IOException {
        return socketConnection.sendData(data);
    }

    @Override
    public void close() throws IOException {
        socketConnection.close();
    }
}
