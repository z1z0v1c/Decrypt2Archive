
import client.Client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
            throw new Exception("Second argument must be number");
        }
        String pathToInputDir = args[2];       
        
        Client client = new Client();
        client.connect(serverAddress, portNumber);
        String response = client.sendDataToServer(pathToInputDir);
        System.out.println(response);
        client.disconnect();
    }
}
