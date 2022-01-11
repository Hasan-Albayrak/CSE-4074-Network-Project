package marmara.app.model;

import marmara.app.StartApp;
import marmara.app.service.ConnectPeer;
import marmara.app.service.RegistryHandlings;
import marmara.app.service.impl.ConnectPeerImpl;
import marmara.app.service.impl.CustomThreadScheduler;
import marmara.app.service.impl.RegistryHandlingsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class ServerThread implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerThread.class);
    private BufferedReader reader;
    private boolean workFlag;
    public static boolean peerLoggedOut;
    private boolean isLast;
    private ConnectPeer connectPeer;

    public void stopThread() {
        workFlag = false;
    }

    public void startThread() {
        workFlag = true;
    }

    @Override
    public void run() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        workFlag = true;
        peerLoggedOut = false;
        connectPeer = new ConnectPeerImpl(new CustomThreadScheduler(5));
        while (!peerLoggedOut) {
            if (workFlag) {
                try {
                    if (peerLoggedOut) {
                        break;
                    }
                    // read the message to deliver.
                    System.out.print("> ");
                    String msg = reader.readLine();
                    if ("logout".equalsIgnoreCase(msg) && !peerLoggedOut) {
                        isLast = false;
                        PeerHandler.peerHandlerMap.forEach((s, peerHandler) -> {
                            if (Objects.nonNull(s) && Objects.nonNull(peerHandler) && !peerHandler.getSocket().isClosed()) {
                                try {
                                    peerHandler.getDos().writeUTF("logout#400");

                                } catch (IOException e) {
                                    LOGGER.error("Error in closing outStream in peer socket while logout ", e);
                                }
                            }
                        });
                        ClientThread.peerLoggedOut = true;
                        break;
                    } else if ("logout-safe".equalsIgnoreCase(msg)) {
                        isLast = true;

                        PeerHandler.peerHandlerMap.forEach((s, peerHandler) -> {
                            if (Objects.nonNull(s) && Objects.nonNull(peerHandler) && !peerHandler.getSocket().isClosed()) {
                                try {
                                    peerHandler.getDos().writeUTF("logout-safe#400");

                                } catch (IOException e) {
                                    LOGGER.error("Error in closing outStream in peer socket while logout ", e);
                                }
                            }
                        });
                        break;
                    } else  if ("connect-peer".equalsIgnoreCase(msg)) {
                        connectPeer.initiate();
                    }else {

                        msg += "#" + StartApp.name;
                        String finalMsg = msg;

                        if (true) {
                            PeerHandler.peerHandlerMap.forEach((s, peerHandler) -> {
                                if (Objects.nonNull(s) && Objects.nonNull(peerHandler)) {
                                    try {
                                        peerHandler.getDos().writeUTF(finalMsg);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } else {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        LOGGER.info("Closing server thread");
        System.out.println("Closing server thread ...");
        RegistryConnection.isChatting = false;
        if (isLast) {

            PeerHandler.peerHandlerMap.forEach((s, peerHandler) -> {
                if (Objects.nonNull(s) && Objects.nonNull(peerHandler) && !peerHandler.getSocket().isClosed()) {
                    try {
                        peerHandler.getDos().close();

                    } catch (IOException e) {
                        LOGGER.error("Error in closing outStream in peer socket while logout-safe ", e);
                    }
                }
            });
            RegistryConnection registryConnection = new RegistryConnection();
            RegistryHandlings registryHandlings = new RegistryHandlingsImpl();
            registryHandlings.connectRegistry(registryConnection, StartApp.name, false);
        }
    }

}

