import picocli.CommandLine;
import application.Server;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.log(Level.INFO, "Starting the server...");

        CommandLine.run(new Server(), args);

        logger.log(Level.INFO, "Shutting down the server...");
    }
}
