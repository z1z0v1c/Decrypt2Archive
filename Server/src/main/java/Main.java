import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;
import application.ConsoleApplication;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.log(Level.INFO, "Starting the application...");

        CommandLine.run(new ConsoleApplication(), args);

        logger.log(Level.INFO, "Shutting down the application...");
    }
}
