package marmara.app;

import marmara.app.model.ChatRequestHandler;
import marmara.app.model.ClientThread;
import marmara.app.model.RegistryConnection;
import marmara.app.model.ServerThread;
import marmara.app.service.RegistryHandlings;
import marmara.app.service.impl.RegistryHandlingsImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartApp {
    public  static  String name;
    public static ServerThread serverThread;
    public static ClientThread clientThread;
    public static int portNumber;

    public static void main(String[] args) throws IOException {

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);

        BufferedReader reader = new BufferedReader(inputStreamReader);

        System.out.println("Enter your username");
        System.out.print(" > ");
        name = reader.readLine();
        System.out.println("Enter your port number");
        System.out.print(" > ");
        portNumber = Integer.parseInt(reader.readLine());

        RegistryHandlings registryHandlings = new RegistryHandlingsImpl();
        RegistryConnection registryConnection = new RegistryConnection();
        ChatRequestHandler chatRequestHandler = new ChatRequestHandler();
        Thread tHandler = new Thread(chatRequestHandler);
        tHandler.start();


        registryHandlings.connectRegistry(registryConnection, name, true);


    }
}
