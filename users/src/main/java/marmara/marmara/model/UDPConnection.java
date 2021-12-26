package marmara.marmara.model;

import java.io.IOException;
import java.net.*;

public class UDPConnection implements Runnable {

    DatagramSocket datagramSocket;
    private DatagramPacket helloPacket;

    public UDPConnection(String username) {
        try {
            datagramSocket = new DatagramSocket();
            String udpMsg = "HELLO" + " " + username;
            byte[] byteHello = udpMsg.getBytes();
            helloPacket = new DatagramPacket(byteHello, byteHello.length, InetAddress.getLocalHost(), 5555);
        } catch (UnknownHostException | SocketException e) {
            System.err.println(e);
        }

    }

    @Override
    public void run() {

        while (true) {
            try {

                Thread.sleep(60000);
                datagramSocket.send(helloPacket);
            } catch (InterruptedException | IOException e) {
                System.err.println(e);
            }


        }


    }
}
