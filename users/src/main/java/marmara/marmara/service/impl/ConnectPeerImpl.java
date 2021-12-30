package marmara.marmara.service.impl;

import marmara.marmara.model.Peer;
import marmara.marmara.service.ConnectPeer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ConnectPeerImpl implements ConnectPeer {

    private static Logger LOGGER = LoggerFactory.getLogger(ConnectPeerImpl.class);


    public void connect(String ipAddresss, int portNumber, ServerSocket chatTCPSocket, String userName) {

        Socket newRequestSocket = null;

        DataInputStream inputStream = null;
        DataOutputStream dos = null;
        Peer peerToConnect = Peer.builder().username(userName).portNumber(String.valueOf(portNumber)).build();

        try {
            newRequestSocket = chatTCPSocket.accept();

            inputStream = new DataInputStream(newRequestSocket.getInputStream());
            dos = new DataOutputStream(newRequestSocket.getOutputStream());

            PeerHandler newPeerHandler = PeerHandler.builder().peer(peerToConnect).dis(inputStream).dos(dos).scn(new Scanner(System.in)).socket(newRequestSocket).build();
            PeerHandler.peerHandlerMap.put(userName, newPeerHandler);

        } catch (Exception e) {
            LOGGER.error("Error in initiating peer connection", e);
        }

    }
}
