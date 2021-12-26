package marmara;

import marmara.model.Registry;

import java.io.IOException;

public class StartRegistry {
    public static void main(String[] args) throws IOException {


        Registry registry = new Registry();
        registry.multiThreadedServer();

    }
}
