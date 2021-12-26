package marmara.service.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class CheckOnline implements Runnable {

    private final DatagramSocket datagramSocket;
    private final DatagramPacket datagramPacket;

    public CheckOnline(DatagramSocket datagramSocket, DatagramPacket datagramPacket) throws SocketException {
        this.datagramSocket = datagramSocket;
        this.datagramPacket = datagramPacket;
    }

    @Override
    public void run() {

        System.out.println("UDPListener listening at port " + datagramSocket.getPort());

        String parsedPacket = "";
        while (true) {
            try {
                datagramSocket.receive(datagramPacket);
                System.out.println("Got a package ..!!!");
                parsedPacket = new String(datagramPacket.getData()); // "HELLO <username>"  TODO şeklinde geliyor parslanıp user listesinde aranıcak
                System.out.println(parsedPacket);
            } catch (IOException e) {
                System.err.println(e);
            }
        }

        private void updateStatus(String username){



        }

    }
}



