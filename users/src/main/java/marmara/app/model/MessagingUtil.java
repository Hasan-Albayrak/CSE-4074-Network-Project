package marmara.app.model;

import java.util.StringTokenizer;

public class MessagingUtil {

    public static Message createMessage(String message, String code){
        return Message.builder()
                .messagePart(message)
                .code(code)
                .token("#")
                .build();
    }

    public static Message decode(String s){
        StringTokenizer st = new StringTokenizer(s, "#");
        return Message.builder()
                .messagePart(st.nextToken())
                .code(st.nextToken())
                .token("#")
                .build();
    }
}
