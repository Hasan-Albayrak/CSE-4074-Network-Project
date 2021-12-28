package marmara.marmara.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import marmara.marmara.service.ChatWithPeer;
import marmara.marmara.service.ConnectPeer;
import marmara.marmara.service.RegistryHandlings;
import marmara.marmara.service.impl.RegistryHandlingsImpl;

@Data
@AllArgsConstructor
public class Peer  {

    private final String username;
    private final String password;
    private final RegistryHandlingsImpl registryHandlingsImpl;



}
