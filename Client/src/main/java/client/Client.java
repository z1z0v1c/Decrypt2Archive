package client;

import java.net.*;
import java.io.*;

public class Client {

    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;

    public void connect(String address, int port) {
        // establish a connection 
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");           

            // sends output to the socket 
            out = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public String sendDataToServer(String data) { 
        String response = "";
        try {
            out.writeUTF(data);
            input = new DataInputStream( 
                new BufferedInputStream(socket.getInputStream()));
            response = input.readUTF();
        } catch (IOException i) {
            System.out.println(i);
        }
        return response;
    }

    public void disconnect() {      
        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}
