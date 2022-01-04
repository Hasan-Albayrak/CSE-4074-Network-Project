package marmara.model;

import marmara.service.PeerFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Registry implements PeerFinder {
    private static Logger LOGGER = LoggerFactory.getLogger(Registry.class);

    public static Map<String, UserHandler> userHandlerMap = new HashMap<>();
    public static Map<String, User> users = new HashMap<>();
    private static int numberOfUsers = 0;
    private Socket socket;
    private ServerSocket server;
    private DataInputStream in;
    private ServerSocket multiThreadSocket;

    public void multiThreadedServer() throws IOException {
        // server is listening on port 8080
        multiThreadSocket = new ServerSocket(8080);
        LOGGER.info("Server started. Listening at port {}", multiThreadSocket.getLocalPort());

        LOGGER.info("Waiting for client ...");

        Socket newRequestSocket = null;

        DataInputStream inputStream = null;
        DataOutputStream dos = null;
        // running infinite loop for getting
        // client request
        while (true) {

            try {
                // Accept the incoming request
                newRequestSocket = multiThreadSocket.accept();
                LOGGER.info("New client request received : {}" , newRequestSocket);

                // obtain input and output streams
                inputStream = new DataInputStream(newRequestSocket.getInputStream());
                dos = new DataOutputStream(newRequestSocket.getOutputStream());

                LOGGER.info("Creating a new handler for this client ...");

            } catch (IOException e) {
                LOGGER.error("IO error in newHandler socket", e);
            }
            UserHandler newUserHandler = new UserHandler(newRequestSocket, "client " + numberOfUsers, inputStream, dos);

            // Create a new Thread with this object.
            Thread t = new Thread(newUserHandler);

            LOGGER.info("Adding this client to activate client list");

            // add this client to active clients list
            userHandlerMap.put("client " + numberOfUsers, newUserHandler);

            // start the thread.
            t.start();
            numberOfUsers++;

            LOGGER.info("Waiting for a client ...");

        }
    }
}
