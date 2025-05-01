import client.Client;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        CommandLine.run(new Client(), args);
    }
}
