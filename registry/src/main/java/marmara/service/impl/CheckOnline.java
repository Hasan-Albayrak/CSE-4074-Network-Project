package marmara.service.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class CheckOnline implements Runnable{
   private final DatagramSocket datagramSocket ;//= new DatagramSocket(5555);
   private final DatagramPacket datagramPacket;
    public CheckOnline(DatagramSocket datagramSocket, DatagramPacket datagramPacket) throws SocketException {
        this.datagramSocket = datagramSocket;
        this.datagramPacket = datagramPacket;
    }

    @Override
    public void run() {
        System.out.println("UDP listener listening at port "  + datagramSocket.getPort());

        DatagramPacket receivedPacket = null;
        String parsedPacket;
        while (true){
            try {
                 datagramSocket.receive(receivedPacket);
                System.out.println("Got a package ..!!!");
                parsedPacket = new String(receivedPacket.getData());
                System.out.println(parsedPacket);
            }catch (IOException e){
                System.err.println(e);
            }
        }

    }
}



