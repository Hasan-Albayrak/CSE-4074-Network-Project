package marmara.marmara;

import marmara.marmara.model.Peer;
import marmara.marmara.model.RegistryConnection;
import marmara.marmara.service.RegistryHandlings;
import marmara.marmara.service.impl.RegistryHandlingsImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartApp {

    public static  void main(String[] args) throws IOException {

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);

        BufferedReader reader = new BufferedReader(inputStreamReader);

        System.out.println("Enter your username");
        String username = reader.readLine();
        System.out.println("Enter your password");
        String password = reader.readLine();
//        System.out.println("Enter your IP address");
//        String ipAddress = reader.readLine();
//        System.out.println("Enter your port number");
//        String port = reader.readLine();

        RegistryHandlings registryHandlings = new RegistryHandlingsImpl();
        RegistryConnection registryConnection = new RegistryConnection();

       // Peer peer = new Peer(username, password, new RegistryHandlingsImpl());
        registryHandlings.connectRegistry(registryConnection);
       // peer.connectRegistry(registryConnection);



    }
}
