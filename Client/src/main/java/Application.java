import client.Connection;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Aleksandar Zizovic
 */
public class Application {
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    private String serverAddress;
    private int portNumber;
    private String inputDirectory;

    public void parse(String[] args) {
        if (args.length != 3) {
            logger.log(Level.SEVERE, "Number of args must be 3!");
            System.exit(1);
        }

        this.serverAddress = args[0];

        try {
            this.portNumber = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Port argument must be a number: " + args[1]);
            System.exit(1);
        }

        this.inputDirectory = args[2];
    }


    public static void main(String[] args) {
        Application app = new Application();

        app.parse(args);

        try {
            logger.log(Level.INFO, "Connecting to the server...");

            // Connect to the server
            Connection connection = new Connection(app.serverAddress, app.portNumber);

            logger.log(Level.INFO, "Connected.");

            // Send data to the server and receive the response
            String response = connection.sendRequest(app.inputDirectory);

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
