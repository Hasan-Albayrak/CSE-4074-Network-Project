package marmara.app.model;

import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Builder
public class PeerHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PeerHandler.class);
    public static Map<String, PeerHandler> peerHandlerMap = new HashMap<>();


    private final DataInputStream dis;
    private final DataOutputStream dos;
    private Scanner scn;
    private String name;
    private Socket socket;
    private boolean isOnline;
    private BufferedReader bufferedReader;
    private Peer peer;
    private AtomicInteger lastPing;


}
