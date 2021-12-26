package marmara.service.impl;

import marmara.model.TCPConnection;
import marmara.model.UserHandler;
import marmara.service.CheckOnline;

public class CheckOnlineImpl implements CheckOnline {
    @Override
    public boolean sendUDPCheck(UserHandler userHandler) {
        return false;
    }

    @Override
    public TCPConnection registerUser(UserHandler userHandler) {
        return null;
    }
}
