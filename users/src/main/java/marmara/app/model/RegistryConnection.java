package marmara.app.model;

import lombok.Data;
import marmara.app.service.impl.ConnectPeerImpl;
import marmara.app.service.impl.CustomThreadScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;


@Data
public class RegistryConnection extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistryConnection.class);

    // initialize socket and input output streams
    public static boolean isChatting = false;
    private Socket socket;

    private BufferedReader bufferedReader;
    private InputStreamReader input;

    private BufferedOutputStream bufferedOutputStream;
    private DataOutputStream out;

    private BufferedInputStream bufferedServerInputStream;
    private DataInputStream serverIn;

    private ConnectPeerImpl connectPeer;


    @Override
    public void run() {

        connectPeer = new ConnectPeerImpl(new CustomThreadScheduler(5));
        input = new InputStreamReader(System.in);
        System.out.println("If nothing is prompted to you you can write 'ping' to get update from registry");
        bufferedReader = new BufferedReader(input);
        bufferedOutputStream = new BufferedOutputStream(out);
        bufferedServerInputStream = new BufferedInputStream(serverIn);
        // string to read message from input
        final String[] serverInput = new String[1];
        final String[] serverOutput = {"null"};
        final StringTokenizer[] st = {null};
        final String[] msg = new String[1];
        final String[] code = {"null"};

        // keep reading until "Logout" is input
        if (true) {
            // sendMessage thread
            Thread sendMessage = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        while (!isChatting && socket != null && !socket.isClosed()){

                            try {
                              //  System.out.println("User 'connect-peer' command to connect peer");

                                serverOutput[0] = bufferedReader.readLine();
                                if ("connect-peer".equalsIgnoreCase(serverOutput[0])) {
                                    isChatting = true;
                                    out.writeUTF("logout#400");
                                    out.flush();
                                    connectPeer.initiate();
                                    break;
                                }
                                else if ("accept-peer".equalsIgnoreCase(serverOutput[0])) {
                                    isChatting = true;
                                    out.writeUTF("logout#400");
                                    out.flush();
                                    System.out.print(" > ");

                                    break;
                                }
                                else if (!isChatting && socket != null) {
                                    out.writeUTF(serverOutput[0] + "#100");
                                    out.flush();
                                    serverInput[0] = null;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                            break;
                    }
                }
            });

            // readMessage thread
            Thread readMessage = new Thread(new Runnable() {
                @Override
                public void run() {

                    //TODO daha güzel text ui
                    while (true){
                        while (!isChatting && !socket.isClosed() ) {
                            try {
                                serverInput[0] = serverIn.readUTF(); //TODO handle after connecting peer
                                LOGGER.info("Got a message from registry => {}", serverInput[0]);
                                if ("logout".equalsIgnoreCase(serverInput[0])){
                                    socket.close();
                                    break;
                                }
                                // break the string into message and recipient part
                                st[0] = new StringTokenizer(serverInput[0], "#");
                                msg[0] = st[0].nextToken();
                                code[0] = st[0].nextToken();
                                System.out.println("Registry -> " + msg[0]);
                                System.out.print("> ");
                            } catch (IOException e) {
                                LOGGER.error("Error in reading from registry socket", e);
                            }
                        }
                        break;
                    }
                }
            });
            sendMessage.start();
            readMessage.start();
        }
    }
}
