package marmara.marmara.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import marmara.marmara.service.ChatWithPeer;
import marmara.marmara.service.ConnectPeer;
import marmara.marmara.service.RegistryHandlings;
import marmara.marmara.service.impl.PeerHandler;
import marmara.marmara.service.impl.RegistryHandlingsImpl;

@Data
@AllArgsConstructor
@Builder
public class Peer  {

    private final String username;
    private final String portNumber;
    private final PeerHandler peerHandler;



}
