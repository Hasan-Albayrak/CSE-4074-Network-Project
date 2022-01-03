package marmara.marmara;

import marmara.marmara.model.ClientThread;
import marmara.marmara.model.RegistryConnection;
import marmara.marmara.model.ServerThread;
import marmara.marmara.service.RegistryHandlings;
import marmara.marmara.service.impl.RegistryHandlingsImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartApp {
    public  static  String name;
    public static ServerThread serverThread;
    public static ClientThread clientThread;

    public static void main(String[] args) throws IOException {
//        serverThread = new ServerThread();
//        serverThread.stopThread();
//        clientThread = new ClientThread(peerHandler);
//        clientThread.stopThread();

        Thread tServer = new Thread(serverThread);
        tServer.start();
        Thread tClient = new Thread(clientThread);
        tClient.start();

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);

        BufferedReader reader = new BufferedReader(inputStreamReader);

        System.out.println("Enter your username");
        name = reader.readLine();

        RegistryHandlings registryHandlings = new RegistryHandlingsImpl();
        RegistryConnection registryConnection = new RegistryConnection();

        registryHandlings.connectRegistry(registryConnection, name);


    }
}
