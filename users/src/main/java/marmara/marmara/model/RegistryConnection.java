package marmara.marmara.model;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;


@Data
public class RegistryConnection extends Thread {
    private static Logger LOGGER = LoggerFactory.getLogger(RegistryConnection.class);

    // initialize socket and input output streams
    private Socket socket;

    private BufferedReader bufferedReader;
    private InputStreamReader input;

    private BufferedOutputStream bufferedOutputStream;
    private DataOutputStream out;

    private BufferedInputStream bufferedServerInputStream;
    private DataInputStream serverIn;


    @Override
    public void run() {

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

                        try {
                            System.out.print("> ");
                            serverOutput[0] = bufferedReader.readLine();
                            if ("ping".equalsIgnoreCase(serverOutput[0])) {
                                System.out.println("Pong!!");
                            } else {

                                out.writeUTF(serverOutput[0] + "#100");
                                serverInput[0] = null;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            // readMessage thread
            Thread readMessage = new Thread(new Runnable() {
                @Override
                public void run() {

                    while (true) {
                        try {
                            serverInput[0] = serverIn.readUTF();
                            // break the string into message and recipient part
                            st[0] = new StringTokenizer(serverInput[0], "#");
                            msg[0] = st[0].nextToken();
                            code[0] = st[0].nextToken();
                            System.out.println("Registry -> " + msg[0]);
                            LOGGER.info("Got a message from registry => {}", serverInput[0]);
                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                    }
                }
            });

            sendMessage.start();
            readMessage.start();
        } else {

            while (!"Logout".equalsIgnoreCase(serverOutput[0])) {
                try {
                    if (true) {

                        serverInput[0] = serverIn.readUTF();
                        // break the string into message and recipient part
                        st[0] = new StringTokenizer(serverInput[0], "#");
                        msg[0] = st[0].nextToken();
                        code[0] = st[0].nextToken();
                        System.out.println("Registry -> " + msg[0]);
                        LOGGER.info("Got a message from registry => {}", msg[0]);
                    }
                    System.out.print("> ");
                    serverOutput[0] = bufferedReader.readLine();
                    if ("ping".equalsIgnoreCase(serverOutput[0])) {
                        System.out.println("Pong!!");
                    } else {
                       final String toSend = serverOutput[0] + "#100";

                        out.writeUTF(toSend);
                        out.flush();
                        serverInput[0] = null;
                    }


                } catch (IOException e) {
                    LOGGER.error("Error in registry connection IO ", e);

                }
            }

            // close the connection
            try {
                input.close();
                out.close();
                socket.close();
            } catch (IOException i) {
                LOGGER.error("Error in closing data streams in registry connection ", i);
            }

        }
    }
}
