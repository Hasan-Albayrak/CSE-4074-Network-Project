package marmara.marmara.model;

import lombok.Data;

import java.io.*;
import java.net.Socket;


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

        bufferedReader = new BufferedReader(input);
        bufferedOutputStream = new BufferedOutputStream(out);
        bufferedServerInputStream = new BufferedInputStream(serverIn);
        // string to read message from input
        String line = "";

        // keep reading until "Over" is input
        while (!line.equalsIgnoreCase("LOGOUT")) {
            try {
                line = serverIn.readUTF();
                System.out.println("Registry >" + line);
                System.out.print("> ");
                line = bufferedReader.readLine();
                out.writeUTF(line);

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
