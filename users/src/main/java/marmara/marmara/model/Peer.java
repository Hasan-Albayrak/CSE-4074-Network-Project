package marmara.marmara.model;

import marmara.marmara.service.ChatWithPeer;
import marmara.marmara.service.ConnectPeer;
import marmara.marmara.service.RegistryHandlings;

public class Peer implements RegistryHandlings, ChatWithPeer, ConnectPeer {



    @Override
    public TCPConnection connectRegistry(TCPConnection tcpConnection) {
        return null;
    }

    @Override
    public String updateStatus(String iAmOnline) {
        return null;
    }
}
