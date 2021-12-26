package marmara.marmara.model;

import lombok.Data;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;


@Data
public class RegistryConnection extends Thread {

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
        String serverInput = null;
        String serverOutput = null;
        StringTokenizer st = null;
        String msg = null;
        String code = null;

        // keep reading until "Over" is input
        while (!serverInput.equalsIgnoreCase("LOGOUT")) {
            try {

                if (serverInput == null || st == null || "200".equals(code)) {

                    serverInput = serverIn.readUTF();
                    // break the string into message and recipient part
                    st = new StringTokenizer(serverInput, "#");
                    msg = st.nextToken();
                    code = st.nextToken();
                    System.out.println("Registry >" + serverIn);
                } else {

                    System.out.print("> ");
                    serverOutput = bufferedReader.readLine();

                    out.writeUTF(serverOutput);
                    serverInput = null;

                }

            } catch (IOException i) {
                System.out.println(i);
            }
        }

        // close the connection
        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }

    }
}
