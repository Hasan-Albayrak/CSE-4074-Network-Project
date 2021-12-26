package marmara;

import marmara.model.Registry;
import marmara.model.TimeOutUser;
import marmara.service.impl.CheckOnline;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class StartRegistry {
    public static void main(String[] args) throws IOException {


        Registry registry = new Registry();
        byte[] udpMsgByteArr = new byte[1024];

        CheckOnline checkOnline = new CheckOnline(new DatagramSocket(5555), new DatagramPacket(udpMsgByteArr, udpMsgByteArr.length));
        Thread udpListenerThread = new Thread(checkOnline);
        udpListenerThread.start();
        TimeOutUser timeOutUser = new TimeOutUser();
        Thread timeOutUserThread = new Thread(timeOutUser);
        timeOutUserThread.start();
        registry.multiThreadedServer();

    }
}
