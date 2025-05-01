package server;

import picocli.CommandLine.Option;

import java.io.IOException;

/**
 * @author Aleksandar Zizovic
 */
public class Server implements Runnable {
    @Option(names = {"-p", "--port-number"}, defaultValue = "8888", description = "Server port number")
    private static int portNumber;
    @Option(names = {"-d", "--database"}, defaultValue = "database.sqlite3", description = "Database location")
    private static String pathToDatabase;
    @Option(names = {"-o", "--output-directory"}, required = true, description = "Path to the output directory")
    private static String pathToOutputDir;


    @Override
    public void run() {
        Connection connection = new Connection(pathToDatabase);
        try {
            connection.start(portNumber, pathToOutputDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
