package marmara.marmara.model;

import marmara.marmara.service.impl.PeerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.StringTokenizer;

public class ClientThread implements Runnable {

    private final PeerHandler peerHandler;
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientThread.class);


    public static boolean peerLoggedOut;

    private boolean  workFlag;

    public ClientThread(PeerHandler peerHandler) {
        this.peerHandler = peerHandler;
    }

    public void stopThread() {
        workFlag = false;
    }
    public void startThread(){
        workFlag = true;
    }

    @Override
    public void run() {
        peerLoggedOut = false;
        while (!peerLoggedOut) {
            if (true) {

                try {
                    String msg = peerHandler.getDis().readUTF();
                    LOGGER.info("Got a message from a peer => {}", msg);
                    StringTokenizer st = new StringTokenizer(msg, "#");
                    String msgToRead = st.nextToken();
                    String peerName = st.nextToken();
                    if ("logout".equalsIgnoreCase(msgToRead)){
                        peerHandler.getDis().close();
                        ServerThread.peerLoggedOut = true;
                        break;
                    }
                    System.out.println(peerName + " > " + msgToRead);

                } catch (IOException e) {
                    LOGGER.error("Error while reading message from a peer => ", e);
                }

                // read the message sent to this client
//                PeerHandler.peerHandlerMap.forEach((s, peerHandler) -> {
//                    if (Objects.nonNull(s) && Objects.nonNull(peerHandler)) {
//
//                        try {
//                            String msg = peerHandler.getDis().readUTF();
//                            LOGGER.info("Got a message from a peer => {}", msg);
//                            StringTokenizer st = new StringTokenizer(msg, "#");
//                            String msgToRead = st.nextToken();
//                            String peerName = st.nextToken();
//                            System.out.println(peerName + " > " + msgToRead);
//
//                        } catch (IOException e) {
//                            LOGGER.error("Error while reading message from a peer => ", e);
//                        }
//                    }
//                });
            }
        }
        LOGGER.info("Closing client thread {} logged out", peerHandler.getName());
        System.out.println(peerHandler.getName() + " logged out");
        RegistryConnection.isChatting = false;
    }
}



