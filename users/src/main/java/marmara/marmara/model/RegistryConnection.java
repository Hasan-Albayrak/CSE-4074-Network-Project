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
        String serverInput;
        String serverOutput = "null";
        StringTokenizer st = null;
        String msg;
        String code = "null";

        // keep reading until "Logout" is input
        while (!"Logout".equalsIgnoreCase(serverOutput)) {
            try {
                if (st == null || "200".equals(code)) {

                    serverInput = serverIn.readUTF();
                    // break the string into message and recipient part
                    st = new StringTokenizer(serverInput, "#");
                    msg = st.nextToken();
                    code = st.nextToken();
                    System.out.println("Registry -> " + msg);
                }

                System.out.print("> ");
                serverOutput = bufferedReader.readLine();
                if ("ping".equalsIgnoreCase(serverOutput)){
                    System.out.println("Pong!!");
                }
                else{

                    out.writeUTF(serverOutput);
                    serverInput = null;
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
