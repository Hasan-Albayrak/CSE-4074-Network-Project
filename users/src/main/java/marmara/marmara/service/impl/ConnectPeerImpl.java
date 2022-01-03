package marmara.marmara.service.impl;

import marmara.marmara.StartApp;
import marmara.marmara.model.ClientThread;
import marmara.marmara.model.Peer;
import marmara.marmara.model.ServerThread;
import marmara.marmara.service.ConnectPeer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ConnectPeerImpl implements ConnectPeer {

    private static Logger LOGGER = LoggerFactory.getLogger(ConnectPeerImpl.class);
    private final CustomThreadScheduler myScheduler;

    public ConnectPeerImpl(CustomThreadScheduler myScheduler) {
        this.myScheduler = myScheduler;
    }

    public void initiate(){
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Enter peer portNumber and userName like portNumber-userName");

    }


    public void connect(String ipAddresss, int portNumber, ServerSocket chatTCPSocket, String userName) {

        Socket newRequestSocket = null;

        DataInputStream inputStream = null;
        DataOutputStream dos = null;
        Peer peerToConnect = Peer.builder().username(userName).portNumber(String.valueOf(portNumber)).build();

        try {
            newRequestSocket = chatTCPSocket.accept();
            Socket socket = new Socket(InetAddress.getLocalHost(), portNumber);



            inputStream = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(StartApp.name + "#");
            String yesOrNo = inputStream.readUTF();
            if ("accept".equalsIgnoreCase(yesOrNo)){


                PeerHandler newPeerHandler = PeerHandler.builder().peer(peerToConnect).dis(inputStream).dos(dos).scn(new Scanner(System.in)).socket(newRequestSocket).build();
                PeerHandler.peerHandlerMap.put(userName, newPeerHandler);
                ClientThread clientThread = new ClientThread(newPeerHandler);
                ServerThread serverThread = new ServerThread();
                myScheduler.execute(clientThread);
                myScheduler.execute(serverThread);
            }else {
                System.out.println("Peer refused connection");
            }


        } catch (Exception e) {
            LOGGER.error("Error in initiating peer connection", e);
        }

    }
}
