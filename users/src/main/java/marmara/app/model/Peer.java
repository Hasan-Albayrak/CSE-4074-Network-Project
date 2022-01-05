package marmara.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Peer  {

    private final String username;
    private final String portNumber;
    private final PeerHandler peerHandler;



}
