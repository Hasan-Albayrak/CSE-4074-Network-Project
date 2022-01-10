package marmara.app.service.impl;

import marmara.app.StartApp;
import marmara.app.model.*;
import marmara.app.service.ConnectPeer;
import marmara.app.service.RegistryHandlings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ConnectPeerImpl implements ConnectPeer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectPeerImpl.class);
    private final CustomThreadScheduler myScheduler;

    public ConnectPeerImpl(CustomThreadScheduler myScheduler) {
        this.myScheduler = myScheduler;
    }

    public void initiate() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter peer portNumber and userName like portNumber-userName ");
        System.out.print(" > ");

        try {
            while (reader.ready()) {
                reader = new BufferedReader(new InputStreamReader(System.in));
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        String portNumberUsername = null;
        try {

            portNumberUsername = reader.readLine();
        } catch (Exception e) {
            LOGGER.error("Error in reading peer port number and name ", e);
        }
        StringTokenizer st = new StringTokenizer(portNumberUsername, "-");
        connect(Integer.parseInt(st.nextToken()), st.nextToken());

    }


    public void connect(int portNumber, String userName) {

        DataInputStream inputStream = null;
        DataOutputStream dos = null;
        Peer peerToConnect = Peer.builder().username(userName).portNumber(String.valueOf(portNumber)).build();
        Socket socket = null;

        try {
            socket = new Socket(InetAddress.getLocalHost(), portNumber);
            System.out.println("Connected peer, Awaiting response ...");


            inputStream = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(StartApp.name + "#");
            String yesOrNo = inputStream.readUTF();
            if ("accept".equalsIgnoreCase(yesOrNo)) {
                PeerHandler newPeerHandler = PeerHandler.builder()
                        .peer(peerToConnect)
                        .dis(inputStream)
                        .dos(dos)
                        .scn(new Scanner(System.in))
                        .socket(socket)
                        .name(userName)
                        .build();
                PeerHandler.peerHandlerMap.put(userName, newPeerHandler);
                ClientThread clientThread = new ClientThread(newPeerHandler);
                ServerThread serverThread = new ServerThread();
                myScheduler.execute(clientThread);
                myScheduler.execute(serverThread);
            }else {
                RegistryConnection.isChatting = false;
                System.out.println("Peer refused connection");
                LOGGER.info("Peer refused connection");
                RegistryConnection registryConnection = new RegistryConnection();
                RegistryHandlings registryHandlings = new RegistryHandlingsImpl();
                registryHandlings.connectRegistry(registryConnection, StartApp.name, false);
            }


        } catch (Exception e) {
            LOGGER.error("Error in initiating peer connection", e);
        }


    }

    public void connect(String ipAddresss, int portNumber, String userName) {

        DataInputStream inputStream = null;
        DataOutputStream dos = null;
        Peer peerToConnect = Peer.builder().username(userName).portNumber(String.valueOf(portNumber)).build();
        Socket socket = null;

        try {
            socket = new Socket(ipAddresss, portNumber);
            System.out.println("Connected peer, Awaiting response ...");


            inputStream = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(StartApp.name + "#");
            String yesOrNo = inputStream.readUTF();
            if ("accept".equalsIgnoreCase(yesOrNo)) {
                PeerHandler newPeerHandler = PeerHandler.builder()
                        .peer(peerToConnect)
                        .dis(inputStream)
                        .dos(dos)
                        .scn(new Scanner(System.in))
                        .socket(socket)
                        .name(userName)
                        .build();
                PeerHandler.peerHandlerMap.put(userName, newPeerHandler);
                ClientThread clientThread = new ClientThread(newPeerHandler);
                ServerThread serverThread = new ServerThread();
                myScheduler.execute(clientThread);
                myScheduler.execute(serverThread);
            } else {
                RegistryConnection.isChatting = false;
                System.out.println("Peer refused connection");
                LOGGER.info("Peer refused connection");
                RegistryConnection registryConnection = new RegistryConnection();
                RegistryHandlings registryHandlings = new RegistryHandlingsImpl();
                registryHandlings.connectRegistry(registryConnection, StartApp.name, false);
            }


        } catch (Exception e) {
            LOGGER.error("Error in initiating peer connection", e);
        }


    }
}
