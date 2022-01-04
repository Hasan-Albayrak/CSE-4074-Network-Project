package marmara.app.model;

import marmara.app.StartApp;

public class ThreadsUtil {
    private ServerThread serverThread = StartApp.serverThread;
    private ClientThread clientThread = StartApp.clientThread;
    public void startServerThread(){

        serverThread.startThread();
    }

    public void stopServerThread(){

        serverThread.stopThread();
    }

    public void startClientTread(){


        clientThread.startThread();
    }

    public void stopClientTread(){

        clientThread.stopThread();

    }
}
