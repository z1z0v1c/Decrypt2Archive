package application;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import network.SocketConnection;
import picocli.CommandLine.Option;

/**
 * @author Aleksandar Zizovic
 */
public class Client implements Runnable {
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    @Option(names = {"-s", "--server-address"}, defaultValue = "localhost", description = "Server address")
    private String serverAddress;

    @Option(names = {"-p", "--port-number"}, defaultValue = "8888", description = "Server port number")
    private int portNumber;

    @Option(names = {"-i", "--input-directory"}, required = true, description = "Path to the input directory on the server")
    private String inputDirectory;

    private SocketConnection socketConnection;

    @Override
    public void run() {
        try {
            logger.log(Level.INFO, "Connecting to the server...");

            // Connect to the server
            socketConnection = new SocketConnection(serverAddress, portNumber);

            logger.log(Level.INFO, "Connection established.");
            logger.log(Level.INFO, "Sending request to the server...");

            // Send input directory
            String response = socketConnection.sendData(inputDirectory);

            logger.log(Level.INFO, String.format("Response: %s", response));
            logger.log(Level.INFO, "Disconnecting from the server...");

            // Disconnect from the server and close resources
            // Not using auto-closeable intentionally
            socketConnection.close();

            logger.log(Level.INFO, "Disconnected successfully.");
        } catch (IllegalArgumentException | UnknownHostException ex) {
            logger.log(Level.SEVERE, String.format("Invalid argument: %s", ex.getMessage()));
            System.exit(1);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            System.exit(1);
        }
    }
}
