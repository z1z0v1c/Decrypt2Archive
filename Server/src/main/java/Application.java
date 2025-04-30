import server.Server;

/**
 * @author Aleksandar Zizovic
 */
public class Application {
    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Number of args must be 3!");
        }

        int portNumber;

        try {
            portNumber = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Second argument must be number");
        }

        String pathToDatabase = args[1];
        String pathToOutputDir = args[2];

        Server server = new Server(pathToDatabase);
        server.start(portNumber, pathToOutputDir);
    }
}
