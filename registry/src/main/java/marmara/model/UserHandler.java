package marmara.model;

import lombok.Data;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class UserHandler implements Runnable {

    private static Logger LOGGER = LoggerFactory.getLogger(UserHandler.class);


    private final DataInputStream dis;
    private final DataOutputStream dos;
    private Scanner scn;
    private String name;
    private Socket socket;
    private boolean isOnline;
    private BufferedReader bufferedReader;
    private User user;
    private AtomicInteger lastPing;


    public UserHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos) {
        this.scn = new Scanner(System.in);
        this.name = name;
        this.dis = dis;
        this.dos = dos;
        this.socket = s;
        lastPing = new AtomicInteger(0);
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    private void createAccount() throws IOException {

        String promptForUsername = "Enter a username to login " +
                "(if you do not have an account enter we will create it for you)" + "#200";
        String promptForPassword = "Enter your password" + "#200";
        boolean isUsernameValid = false;

        String username = "";
        String password = "";
        User connectingUser;
        StringTokenizer st;

        while (!isUsernameValid) {

            dos.writeUTF(promptForUsername);
            username = new StringTokenizer(dis.readUTF()).nextToken("#");

            if (Registry.users.containsKey(username)) {
                connectingUser = Registry.users.get(username);
                dos.writeUTF("Welcome back \n" + promptForPassword);
                dos.flush();

                st = new StringTokenizer(dis.readUTF(), "#");
                password = st.nextToken();
                if (connectingUser.getPassword().equals(password)) {

                    dos.writeUTF("Logged in successfully!!" + "#200");
                    dos.flush();
                    isUsernameValid = true;
                    this.user = connectingUser;
                } else {
                    dos.writeUTF("Wrong password!!!" + "#300");
                    dos.flush();
                }
            } else {

                dos.writeUTF("Creating new user. Please enter your password" + "#200");
                dos.flush();
                st = new StringTokenizer(dis.readUTF(), "#");
                password = st.nextToken();

                dos.writeUTF("Enter a port number for others to chat with you" + "#200");
                dos.flush();

                st = new StringTokenizer(dis.readUTF(), "#");
                String port = st.nextToken();

                dos.writeUTF("Enter a port number for us to check you" + "#200");
                dos.flush();
                st = new StringTokenizer(dis.readUTF(), "#");
                String udpPort = st.nextToken();

                this.user = User.builder()
                        .username(username)
                        .password(password)
                        .ipAddress("local")
                        .chatPortNumber(Integer.parseInt(port))
                        .checkOnlinePortNumber(Integer.parseInt(udpPort))
                        .build();

                dos.writeUTF("Congrats, your account has been created!!!" + "#200");
                dos.flush();
                LOGGER.info("new user account has been created => {}", user);
                Registry.users.put(this.user.getUsername(),this.user);
                this.isOnline = true;

                isUsernameValid = true;

            }
        }

    }

    @SneakyThrows
    @Override
    public void run() {
        createAccount();

        String choicesString = "Use '-Search  <username>' to search for other users.\n" +
                "               Use '-Info' to get your account details\n" +
                "               User '-Logout' to exit system\n";


        String received;
        while (this.isOnline) {

            try {
                dos.writeUTF(choicesString + "Enter your choice" + "#200");
                dos.flush();
                // receive the string
                received = dis.readUTF();
                StringTokenizer st = new StringTokenizer(received, "#");
                String msgPart = st.nextToken();
                String code = st.nextToken();

                LOGGER.info("Received Message from User -> {} -> {}", user.getUsername(), received);

                if (msgPart.equalsIgnoreCase("logout")) {
                    this.isOnline = false;
                    dos.writeUTF("logout");
                  //  this.socket.close();
                    break;
                }
                switch (msgPart.split(" ")[0].toUpperCase()) {
                    case "-SEARCH":
                        dos.writeUTF(search(msgPart));
                        break;
                    case "-LOGOUT":
                        logout();
                        break;
                    case "-INFO":
                        dos.writeUTF(info());
                        dos.flush();
                        break;
                    default:

                }

                // break the string into message and recipient part


            } catch (IOException e) {

                LOGGER.error("IO error in Userhandler while talking to user ", e);
            }

        }
        try {
            // closing resources
            this.dis.close();
            this.dos.close();
            this.socket.close();

        } catch (IOException e) {
            LOGGER.error("IO error in closing datastreams in user comms ", e);
        }
    }

    private String search(String msg) {
       // String userName = Arrays.stream(msg.split(" ")).filter(s -> !"Search".equalsIgnoreCase(s)).toString();
        String userName = msg.split(" ")[1];
        if (Registry.users.containsKey(userName)){

            return (Registry.users.get(userName).getChatPortNumber() + "#300");
        }
        else {
            return "Could not found user#400";
        }

    }

    private String info() {

        return "Your info username -> " + user.getUsername() + " chat port number -> " + user.getChatPortNumber() + "#200";
    }

    private void logout() {


    }

}
