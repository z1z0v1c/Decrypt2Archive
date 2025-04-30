import client.Client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Aleksandar Zizovic
 */
public class Application {
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        if (args.length != 3) {
            logger.log(Level.SEVERE, "Number of args must be 3!");
            System.exit(1);
        }

        String serverAddress = args[0];

        // System.exit call is not recognized by the compiler
        // Therefore, the value must be initialized
        int portNumber = -1;

        try {
            portNumber = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Port argument must be a number: " + args[1]);
            System.exit(1);
        }

        String pathToInputDir = args[2];

        Client client = new Client();

        try {
            logger.log(Level.INFO, "Connecting to the server...");

            // Connect to the server
            client.connect(serverAddress, portNumber);

            logger.log(Level.INFO, "Connected.");

            // Send data to the server and receive the response
            String response = client.sendRequest(pathToInputDir);

            logger.log(Level.INFO, String.format("Response: %s", response));
            logger.log(Level.INFO, "Disconnecting and closing the application...");

            // Close all client resources
            client.disconnect();
        } catch (IllegalArgumentException | IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            System.exit(1);
        }
    }
}
