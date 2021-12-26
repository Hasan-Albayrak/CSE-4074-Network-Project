package marmara.marmara.service.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import marmara.marmara.model.ClientThread;
import marmara.marmara.model.RegistryConnection;
import marmara.marmara.model.TCPConnection;
import marmara.marmara.service.RegistryHandlings;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

@Builder
@AllArgsConstructor
public class RegistryHandlingsImpl implements RegistryHandlings {

    InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(inputStreamReader);

    public RegistryHandlingsImpl() {
        this.inputStreamReader = new InputStreamReader(System.in);
        this.reader = new BufferedReader(inputStreamReader);
    }

    @Override
    public void connectRegistry(RegistryConnection registryConnection) {

        // establish a connection
        try
        {
            String address = InetAddress.getLocalHost().getHostAddress();
            System.out.println("Give me port number registry connection");
            String port = reader.readLine();

            registryConnection.setSocket(new Socket(address, Integer.parseInt(port)));
            System.out.println("Connected");

            // takes input from terminal
            registryConnection.setInput(new DataInputStream(System.in));

            // sends output to the socket
            registryConnection.setOut(new DataOutputStream(registryConnection.getSocket().getOutputStream()));
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

        registryConnection.run();
    }

    @Override
    public String updateStatus(String iAmOnline) {
        return null;
    }

}
