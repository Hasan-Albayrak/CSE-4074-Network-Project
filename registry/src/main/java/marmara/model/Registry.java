package marmara.model;

import marmara.service.CheckOnline;
import marmara.service.PeerFinder;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Registry implements PeerFinder, CheckOnline {

    private static int numberOfUsers = 0;
    public static Map<String, UserHandler> userHandlerMap = new HashMap<>();
    public static Map<String, User> users = new HashMap<>();
    private Socket socket;
    private ServerSocket server;
    private DataInputStream in;
    private ServerSocket multiThreadSocket;

    @Override
    public boolean sendUDPCheck(UserHandler userHandler) {
        return false;
    }

    @Override
    public TCPConnection registerUser(UserHandler userHandler) {
        return null;
    }

    public void startServer() {

        // starts server and waits for a connection
        try {
            server = new ServerSocket(1234);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");

            // takes input from the client socket
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));

            String line = "";

            // reads message from client until "Over" is sent
            while (!line.equals("LOGOUT")) {
                try {
                    line = in.readUTF();
                    System.out.println(line);

                } catch (IOException i) {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");

            // close connection
            socket.close();
            in.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }


    public void multiThreadedServer() throws IOException {
        // server is listening on port 8080
        multiThreadSocket = new ServerSocket(8080);
        System.out.println("Server started. Listening at port 8080");

        System.out.println("Waiting for a client ...");

        Socket newRequestSocket = null;

        DataInputStream inputStream = null;
        DataOutputStream dos = null;
        // running infinite loop for getting
        // client request
        while (true) {

            try {
                // Accept the incoming request
                newRequestSocket = multiThreadSocket.accept();
                System.out.println("New client request received : " + newRequestSocket);

                // obtain input and output streams
                inputStream = new DataInputStream(newRequestSocket.getInputStream());
                dos = new DataOutputStream(newRequestSocket.getOutputStream());

                System.out.println("Creating a new handler for this client...");

            } catch (IOException e) {
                System.err.println(e);
            }
            UserHandler newUserHandler = new UserHandler(newRequestSocket, "client " + numberOfUsers, inputStream, dos);

            // Create a new Thread with this object.
            Thread t = new Thread(newUserHandler);

            System.out.println("Adding this client to active client list");

            // add this client to active clients list
            userHandlerMap.put("client " + numberOfUsers ,newUserHandler);

            // start the thread.
            t.start();
            numberOfUsers++;

            System.out.println("Waiting for a client ...");

        }
    }
}
