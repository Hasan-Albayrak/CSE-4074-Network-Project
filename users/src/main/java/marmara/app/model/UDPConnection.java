package marmara.app.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;

public class UDPConnection implements Runnable {
    private static Logger LOGGER = LoggerFactory.getLogger(UDPConnection.class);


    DatagramSocket datagramSocket;
    private DatagramPacket helloPacket;

    public UDPConnection(String username) {
        try {
            datagramSocket = new DatagramSocket();
            String udpMsg = "HELLO" + "#" + username + "#";
            byte[] byteHello = udpMsg.getBytes();
            helloPacket = new DatagramPacket(byteHello, byteHello.length, InetAddress.getLocalHost(), 5555);
        } catch (UnknownHostException | SocketException e) {
            LOGGER.error("Error while opening udp to registry ", e);
        }

    }

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(60000);
                datagramSocket.send(helloPacket);
            } catch (InterruptedException | IOException e) {
                LOGGER.error("Error while sending udp to registry ", e);
            }


        }


    }
}
