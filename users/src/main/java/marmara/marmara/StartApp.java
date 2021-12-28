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

        RegistryHandlings registryHandlings = new RegistryHandlingsImpl();
        RegistryConnection registryConnection = new RegistryConnection();

        registryHandlings.connectRegistry(registryConnection, username);



    }
}
