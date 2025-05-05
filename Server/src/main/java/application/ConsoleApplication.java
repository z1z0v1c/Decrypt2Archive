package application;

import picocli.CommandLine.Option;
import server.Server;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Aleksandar Zizovic
 */
public class ConsoleApplication implements Runnable {
    @Option(names = {"-p", "--port-number"}, defaultValue = "8888", description = "Server port number")
    private static int portNumber;

    @Option(names = {"-d", "--database"}, defaultValue = "database.sqlite3", description = "Database location")
    private static String databasePath;

    @Option(names = {"-o", "--output-directory"}, required = true, description = "Path to the output directory")
    private static String outputDirectory;


    @Override
    public void run() {
        try {
            new Server(portNumber, databasePath).serve(outputDirectory);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
