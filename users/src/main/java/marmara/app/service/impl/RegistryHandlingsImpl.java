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

    private static Logger LOGGER = LoggerFactory.getLogger(RegistryHandlingsImpl.class);

    private InputStreamReader inputStreamReader;
    private BufferedReader reader;
    private BufferedInputStream bufferedInputStream;

    public RegistryHandlingsImpl() {
        this.inputStreamReader = new InputStreamReader(System.in);
        this.reader = new BufferedReader(inputStreamReader);
        this.bufferedInputStream = null;
    }

    @Override
    public void connectRegistry(RegistryConnection registryConnection, String username) {

        // establish a connection
        try {
            String address = InetAddress.getLocalHost().getHostAddress();

            int port = 8080;

            registryConnection.setSocket(new Socket(address, port));
            System.out.println("Connected");

            // takes input from terminal
            registryConnection.setInput(new InputStreamReader(System.in));

            // takes input from socket
            // bufferedInputStream = new BufferedInputStream(registryConnection.getSocket().getInputStream());

            registryConnection.setServerIn(new DataInputStream(registryConnection.getSocket().getInputStream()));


            // sends output to the socket
            registryConnection.setOut(new DataOutputStream(registryConnection.getSocket().getOutputStream()));
        } catch (IOException e) {
            LOGGER.error("Error while connecting registry ", e);
        }

        UDPConnection udpConnection = new UDPConnection(username);
        Thread t = new Thread(udpConnection);
        t.start();
        registryConnection.run();

    }


    @Override
    public String updateStatus(String iAmOnline) {
        return null;
    }

}
