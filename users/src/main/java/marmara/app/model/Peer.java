package marmara.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import marmara.app.service.impl.PeerHandler;

@Data
@AllArgsConstructor
@Builder
public class Peer  {

    private final String username;
    private final String portNumber;
    private final PeerHandler peerHandler;



}
