package marmara.marmara.service.impl;

import lombok.Builder;
import marmara.marmara.model.TCPConnection;
import marmara.marmara.service.RegistryHandlings;

@Builder
public class RegistryHandlingsImpl implements RegistryHandlings {

    private final TCPConnection registryConnection;


    @Override
    public TCPConnection connectRegistry(TCPConnection tcpConnection) {
        return null;
    }

    @Override
    public String updateStatus(String iAmOnline) {
        return null;
    }
}
