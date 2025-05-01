import client.Client;
import picocli.CommandLine;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.log(Level.INFO, "Starting the application...");

        CommandLine.run(new Client(), args);

        logger.log(Level.INFO, "Closing the application...");
    }
}
