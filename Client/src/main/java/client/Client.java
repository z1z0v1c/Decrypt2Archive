package client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @Option(names = {"-i", "--input-directory"}, description = "Path to the input directory on the server")
    private String inputDirectory;

    @Override
    public void run() {
        try {
            logger.log(Level.INFO, "Connecting to the server...");

            // Connect to the server
            Connection connection = new Connection(this.serverAddress, this.portNumber);

            logger.log(Level.INFO, "Connected.");

            // Send data to the server and receive the response
            String response = connection.sendRequest(this.inputDirectory);

            logger.log(Level.INFO, String.format("Response: %s", response));
            logger.log(Level.INFO, "Disconnecting and closing the application...");

            // Close all client resources
            connection.disconnect();
        } catch (IllegalArgumentException | IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            System.exit(1);
        }
    }
}
