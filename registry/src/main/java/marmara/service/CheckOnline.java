package marmara.service;

import marmara.model.TCPConnection;
import marmara.model.User;

public interface CheckOnline {

    boolean sendUDPCheck (User user);

    TCPConnection registerUser(User user);
}
