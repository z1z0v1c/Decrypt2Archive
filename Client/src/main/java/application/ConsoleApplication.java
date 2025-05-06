package application;

import client.Client;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.net.UnknownHostException;

/// @author Aleksandar Zizovic
public class ConsoleApplication implements Runnable {
    private static final Logger logger = LogManager.getLogger(ConsoleApplication.class);

    @Option(names = {"-s", "--server-address"}, defaultValue = "localhost", description = "Server address")
    private String serverAddress;

    @Option(names = {"-p", "--port-number"}, defaultValue = "8888", description = "Server port number")
    private int portNumber;

    @Option(names = {"-i", "--input-directory"}, required = true, description = "Path to the input directory on the server")
    private String inputDirectory;


    @Override
    public void run() {
        try (var client = new Client()) {
            logger.log(Level.INFO, String.format("Connecting to the %s server...", serverAddress));

            client.connect(serverAddress, portNumber);

            logger.log(Level.INFO, String.format("Connection established on port %d", portNumber));

            logger.log(Level.INFO, "Sending request to the server...");

            String response = client.sendData(inputDirectory);

            logger.log(Level.INFO, response);

            logger.log(Level.INFO, "Disconnecting from the server...");
        } catch (IllegalArgumentException | UnknownHostException ex) {
            logger.log(Level.ERROR, String.format("Invalid argument: %s", ex.getMessage()));
            System.exit(1);
        } catch (IOException ex) {
            logger.log(Level.ERROR, ex.getMessage());
            System.exit(1);
        }
    }
}
