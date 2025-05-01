package client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jdk.internal.joptsimple.internal.Strings;
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

    private Connection connection;

    @Override
    public void run() {
        connectToTheServer();

        sendInputDirectory();

        disconnectFromTheServer();
    }

    public void connectToTheServer() {
        logger.log(Level.INFO, "Connecting to the server...");

        try {
            connection = new Connection(serverAddress, portNumber);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            System.exit(1);
        }

        logger.log(Level.INFO, "Connection established.");
    }

    public void sendInputDirectory() {
        String response = Strings.EMPTY;

        try {
            response = connection.sendRequest(inputDirectory);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            System.exit(1);
        }

        logger.log(Level.INFO, String.format("Response: %s", response));
    }

    public void disconnectFromTheServer() {
        logger.log(Level.INFO, "Disconnecting from the server...");

        try {
            connection.disconnect();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            System.exit(1);
        }

        logger.log(Level.INFO, "Disconnected successfully.");
    }
}
