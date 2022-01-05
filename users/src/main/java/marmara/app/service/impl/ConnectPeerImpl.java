package marmara.app.service.impl;

import marmara.app.StartApp;
import marmara.app.model.ClientThread;
import marmara.app.model.Peer;
import marmara.app.model.RegistryConnection;
import marmara.app.model.ServerThread;
import marmara.app.service.ConnectPeer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ConnectPeerImpl implements ConnectPeer {

    private static Logger LOGGER = LoggerFactory.getLogger(ConnectPeerImpl.class);
    private final CustomThreadScheduler myScheduler;

    public ConnectPeerImpl(CustomThreadScheduler myScheduler) {
        this.myScheduler = myScheduler;
    }

    public void initiate() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter peer portNumber and userName like portNumber-userName");

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
            e.printStackTrace();
        }
        StringTokenizer st = new StringTokenizer(portNumberUsername, "-");
        connect("local", Integer.parseInt(st.nextToken()), null, st.nextToken());

    }


    public void connect(String ipAddresss, int portNumber, ServerSocket chatTCPSocket, String userName) {

        Socket newRequestSocket = null;

        DataInputStream inputStream = null;
        DataOutputStream dos = null;
        Peer peerToConnect = Peer.builder().username(userName).portNumber(String.valueOf(portNumber)).build();

        try {
            //newRequestSocket = chatTCPSocket.accept();
            Socket socket = new Socket(InetAddress.getLocalHost(), portNumber);
            System.out.println("Connected peer ...");


            inputStream = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(StartApp.name + "#");
            String yesOrNo = inputStream.readUTF();
            if ("accept".equalsIgnoreCase(yesOrNo)){

                PeerHandler newPeerHandler = PeerHandler.builder().peer(peerToConnect).dis(inputStream).dos(dos).scn(new Scanner(System.in)).socket(socket).name(userName).build();
                PeerHandler.peerHandlerMap.put(userName, newPeerHandler);
                ClientThread clientThread = new ClientThread(newPeerHandler);
                ServerThread serverThread = new ServerThread();
                myScheduler.execute(clientThread);
                myScheduler.execute(serverThread);
            }else {
                RegistryConnection.isChatting = false;
                System.out.println("Peer refused connection");
            }


        } catch (Exception e) {
            LOGGER.error("Error in initiating peer connection", e);
        }

    }
}
