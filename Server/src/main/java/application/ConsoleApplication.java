package application;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine.Option;
import server.Server;

import java.io.IOException;
import java.sql.SQLException;

/// @author Aleksandar Zizovic
public class ConsoleApplication implements Runnable {
    private static final Logger logger = LogManager.getLogger(ConsoleApplication.class);

    @Option(names = {"-p", "--port-number"}, defaultValue = "8888", description = "Server port number")
    private static int port;

    @Option(names = {"-d", "--database"}, defaultValue = "database.sqlite3", description = "Database location")
    private static String database;

    @Option(names = {"-o", "--output-file"}, required = true, description = "Path to the output file")
    private static String output;

    @Override
    public void run() {
        // Not combined into single method for better structured logging
        // Also avoiding multiple run methods at different levels
        try (var server = new Server()) {
            logger.log(Level.INFO, "Connecting to the database...");

            server.connectToDatabase(database);

            server.serve(port);

            server.acceptClient();

            server.processRequest(output);

            logger.log(Level.INFO, "Closing the database connection...");
        } catch (IOException | SQLException ex) {
            logger.log(Level.ERROR, ex.getMessage());
            System.exit(1);
        }
    }
}
