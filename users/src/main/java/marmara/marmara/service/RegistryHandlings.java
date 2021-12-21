package marmara.marmara.service;

import marmara.marmara.model.TCPConnection;

public interface RegistryHandlings {

    TCPConnection connectRegistry(TCPConnection tcpConnection);

    String updateStatus(String iAmOnline);



}
