package marmara.service.impl;

import marmara.model.Registry;
import marmara.model.UserHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.StringTokenizer;

public class CheckOnline implements Runnable {
    private static Logger LOGGER = LoggerFactory.getLogger(CheckOnline.class);


    private final DatagramSocket datagramSocket;
    private final DatagramPacket datagramPacket;

    public CheckOnline(DatagramSocket datagramSocket, DatagramPacket datagramPacket) throws SocketException {
        this.datagramSocket = datagramSocket;
        this.datagramPacket = datagramPacket;
    }

    @Override
    public void run() {

        LOGGER.info("UDPListener listening at port {}", datagramSocket.getLocalPort());

        String parsedPacket = "";
        while (true) {
            try {
                datagramSocket.receive(datagramPacket);
                LOGGER.info("Got a package ..!!!");
                parsedPacket = new String(datagramPacket.getData());
                StringTokenizer st = new StringTokenizer(parsedPacket, "#");
                String msg = st.nextToken();
                String userName = st.nextToken();
                updateStatus(userName);
                // System.out.println(parsedPacket);
               // LOGGER.info("Parsed packet => {}", parsedPacket);
                LOGGER.info("Parsed message => {} {}", msg, userName);

            } catch (IOException e) {
                LOGGER.error("Error while receiving udp packages ", e);
            }
        }
    }

    private void updateStatus(String userName) {
        UserHandler userHandler = Registry.userHandlerMap.get(userName);
        if (userHandler != null && userHandler.isOnline()) {
            userHandler.getLastPing().set(0);
        }
    }
}



