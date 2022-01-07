package marmara.app.service.impl;

import lombok.AllArgsConstructor;
import marmara.app.model.RegistryConnection;
import marmara.app.model.UDPConnection;
import marmara.app.service.RegistryHandlings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


@AllArgsConstructor
public class RegistryHandlingsImpl implements RegistryHandlings {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistryHandlingsImpl.class);

    private InputStreamReader inputStreamReader;
    private BufferedReader reader;
    private BufferedInputStream bufferedInputStream;

    public RegistryHandlingsImpl() {
        this.inputStreamReader = new InputStreamReader(System.in);
        this.reader = new BufferedReader(inputStreamReader);
        this.bufferedInputStream = null;
    }

    @Override
    public void connectRegistry(RegistryConnection registryConnection, String username, boolean isFirstTime) {

        // establish a connection
        try {
            String address = InetAddress.getLocalHost().getHostAddress();

            int port = 8080;

            LOGGER.info("Connecting Registry ...");
            System.out.println("Connecting Registry ...");
            registryConnection.setSocket(new Socket(address, port));
            LOGGER.info("Connected");
            System.out.println("Connected");

            // takes input from terminal
            registryConnection.setInput(new InputStreamReader(System.in));

            // takes input from socket
            registryConnection.setServerIn(new DataInputStream(registryConnection.getSocket().getInputStream()));


            // sends output to the socket
            registryConnection.setOut(new DataOutputStream(registryConnection.getSocket().getOutputStream()));
        } catch (IOException e) {
            LOGGER.error("Error while connecting registry ", e);
        }

        if (isFirstTime){

            UDPConnection udpConnection = new UDPConnection(username);
            Thread t = new Thread(udpConnection);
            t.start();
        }
        registryConnection.start();

    }


    @Override
    public String updateStatus(String iAmOnline) {
        return null;
    }

}
