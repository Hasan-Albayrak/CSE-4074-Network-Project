package marmara.app.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {

    private String messagePart;
    private String code;
    private String token;


    public String toString(){
        return null;
    }
}
