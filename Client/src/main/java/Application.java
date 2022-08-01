import client.Client;

/**
 *
 * @author luciano
 */
public class Application {

    public static void main(String args[]) throws Exception {
        if (args.length != 3) {
            throw new Exception("Number of args must be 3!");
        }

        String serverAddress = args[0];
        int portNumber = 0;
        try {
            portNumber = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new Exception("Second argument must be a number");
        }
        String pathToInputDir = args[2];       
        
        Client client = new Client();
        client.connect(serverAddress, portNumber);
        String response = client.sendDataToServer(pathToInputDir);
        System.out.println(response);
        client.disconnect();
    }
}
