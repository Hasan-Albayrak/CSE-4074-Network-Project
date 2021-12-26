package marmara.marmara.model;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.*;
import java.time.Instant;

public class UDPConnection implements Runnable {

    DatagramSocket datagramSocket;
    private static final String HELLO = "HELLO";
    private static final byte[] byteHello = HELLO.getBytes();
    private  DatagramPacket helloPacket;

    public UDPConnection(int port) {
        try{
            datagramSocket = new DatagramSocket();
            helloPacket = new DatagramPacket(byteHello, byteHello.length, InetAddress.getLocalHost(), 5555);
        }catch (UnknownHostException | SocketException e){
            System.err.println(e);
        }

    }

    @Override
    public void run() {

        while (true){
            try {

                Thread.sleep(60000);
                datagramSocket.send(helloPacket);
            }catch (InterruptedException | IOException e){
                System.err.println(e);
            }


        }


    }
}
