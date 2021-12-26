package marmara.model;

import lombok.Data;
import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class UserHandler implements Runnable {


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


        while (!isUsernameValid) {

            dos.writeUTF(promptForUsername);
            username = dis.readUTF();

            if (Registry.users.containsKey(username)) {

                User user = Registry.users.get(username);
                dos.writeUTF("Welcome back \n" + promptForPassword);

                password = dis.readUTF();
                if (user.getPassword().equals(password)) {

                    dos.writeUTF("Logged in successfully!!" + "#200");
                    isUsernameValid = true;
                    this.user = user;
                } else {
                    dos.writeUTF("Wrong password!!!" + "#300");
                }
            } else {

                dos.writeUTF("Creating new user. Please enter your password" + "#200");
                password = dis.readUTF();

                dos.writeUTF("Enter a port number for others to chat with you" + "#200");
                String port = dis.readUTF();

                dos.writeUTF("Enter a port number for us to check you" + "#200");
                String udpPort = dis.readUTF();

                this.user = User.builder()
                        .username(username)
                        .password(password)
                        .ipAddress("local")
                        .chatPortNumber(Integer.parseInt(port))
                        .checkOnlinePortNumber(Integer.parseInt(udpPort))
                        .build();

                dos.writeUTF("Congrats, your account has been created!!!" + "#300");

                isUsernameValid = true;

            }
        }

    }

    @SneakyThrows
    @Override
    public void run() {
        createAccount();

        String choicesString = "";

        String received;
        while (true) {

            try {
                dos.writeUTF("Enter your choice" + "#200");
                // receive the string
                received = dis.readUTF();

                System.out.println(received);

                if (received.equalsIgnoreCase("logout")) {
                    this.isOnline = false;
                    this.socket.close();
                    break;
                }

                // break the string into message and recipient part
                StringTokenizer st = new StringTokenizer(received, "#");
                String MsgToSend = st.nextToken();
                String recipient = st.nextToken();


            } catch (IOException e) {

                e.printStackTrace();
            }

        }
        try {
            // closing resources
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
