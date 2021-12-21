package marmara.model;

import lombok.Builder;

@Builder
public class TCPConnection extends Thread{

    public  final String SenderIP;

    public  final int SenderPortNumber;

    public final String  ReceiverIPAddress;

    private final String ReceiverPortNumber;


}
