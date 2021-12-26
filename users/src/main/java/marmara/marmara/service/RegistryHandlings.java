package marmara.marmara.service;

import marmara.marmara.model.ChatPeer;
import marmara.marmara.model.RegistryConnection;
import marmara.marmara.model.TCPConnection;

public interface RegistryHandlings {

    void connectRegistry(RegistryConnection registryConnection);

    String updateStatus(String iAmOnline);




}
