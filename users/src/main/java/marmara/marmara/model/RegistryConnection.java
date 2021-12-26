package marmara.marmara.model;

import lombok.Builder;
import lombok.Data;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


@Data
public class RegistryConnection extends Thread{

    // initialize socket and input output streams
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream out;

    @Override
    public void run() {

        // string to read message from input
        String line = "";

        // keep reading until "Over" is input
        while (!line.equals("Over"))
        {
            try
            {
                line = input.readLine();
                out.writeUTF(line);
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }

        // close the connection
        try
        {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

    }
}
