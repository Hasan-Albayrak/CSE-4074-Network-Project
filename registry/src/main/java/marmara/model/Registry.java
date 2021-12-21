package marmara.model;

import marmara.service.CheckOnline;
import marmara.service.PeerFinder;

import java.util.List;

public class Registry implements PeerFinder, CheckOnline {

    private TCPConnection registerTCPConnection;


    private List<User> users;

    @Override
    public boolean sendUDPCheck(User user) {
        return false;
    }

    @Override
    public TCPConnection registerUser(User user) {
        return null;
    }
}
