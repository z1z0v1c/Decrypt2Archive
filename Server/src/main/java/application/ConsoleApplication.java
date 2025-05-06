package application;

import picocli.CommandLine.Option;
import server.Server;

import java.io.IOException;
import java.sql.SQLException;

/// @author Aleksandar Zizovic
public class ConsoleApplication implements Runnable {
    @Option(names = {"-p", "--port-number"}, defaultValue = "8888", description = "Server port number")
    private static int port;

    @Option(names = {"-d", "--database"}, defaultValue = "database.sqlite3", description = "Database location")
    private static String database;

    @Option(names = {"-o", "--output-file"}, required = true, description = "Path to the output file")
    private static String output;


    @Override
    public void run() {
        try (var server = new Server()) {
            server.connectToDatabase(database);

            server.serve(port);

            server.processRequest(output);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
