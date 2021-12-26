package marmara.model;

import marmara.service.CheckOnline;
import marmara.service.PeerFinder;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Registry implements PeerFinder, CheckOnline {

    private TCPConnection registerTCPConnection;
    private List<User> users;
    private Socket socket   = null;
    private ServerSocket server   = null;
    private DataInputStream in       =  null;

    @Override
    public boolean sendUDPCheck(User user) {
        return false;
    }

    @Override
    public TCPConnection registerUser(User user) {
        return null;
    }

    public void startServer(){

            // starts server and waits for a connection
            try
            {
                server = new ServerSocket(8080);
                System.out.println("Server started");

                System.out.println("Waiting for a client ...");

                socket = server.accept();
                System.out.println("Client accepted");

                // takes input from the client socket
                in = new DataInputStream(
                        new BufferedInputStream(socket.getInputStream()));

                String line = "";

                // reads message from client until "Over" is sent
                while (!line.equals("Over"))
                {
                    try
                    {
                        line = in.readUTF();
                        System.out.println(line);

                    }
                    catch(IOException i)
                    {
                        System.out.println(i);
                    }
                }
                System.out.println("Closing connection");

                // close connection
                socket.close();
                in.close();
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
    }
}
