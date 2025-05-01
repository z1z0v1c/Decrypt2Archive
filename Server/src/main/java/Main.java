import picocli.CommandLine;
import server.Server;

public class Main {
    public static void main(String[] args) {
        CommandLine.run(new Server(), args);
    }
}
