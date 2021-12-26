package marmara.service;

import marmara.model.TCPConnection;
import marmara.model.UserHandler;

public interface CheckOnline {

    boolean sendUDPCheck (UserHandler userHandler);

    TCPConnection registerUser(UserHandler userHandler);
}
