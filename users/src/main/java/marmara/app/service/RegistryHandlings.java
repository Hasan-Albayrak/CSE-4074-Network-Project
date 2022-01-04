package marmara.app.service;

import marmara.app.model.RegistryConnection;

public interface RegistryHandlings {

    void connectRegistry(RegistryConnection registryConnection, String username);

    String updateStatus(String iAmOnline);




}
