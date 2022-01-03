package marmara;

import marmara.service.impl.CustomThreadScheduler;
import marmara.model.Registry;
import marmara.model.TimeOutUser;
import marmara.service.impl.CheckOnline;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class StartRegistry {

    public static void main(String[] args) throws IOException {

        Registry registry = new Registry();
        CustomThreadScheduler myScheduler = new CustomThreadScheduler(5);
        byte[] udpMsgByteArr = new byte[1024];

        CheckOnline checkOnline = new CheckOnline(new DatagramSocket(5555), new DatagramPacket(udpMsgByteArr, udpMsgByteArr.length));
        myScheduler.execute(checkOnline);

        TimeOutUser timeOutUser = new TimeOutUser();
        ScheduledFuture<?> result = myScheduler.scheduleAtFixedRate(timeOutUser, 100, 100, TimeUnit.SECONDS);
        registry.multiThreadedServer();

    }
}
