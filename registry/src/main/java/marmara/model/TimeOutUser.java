package marmara.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TimeOutUser implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeOutUser.class);

    @Override
    public void run() {

        Set<String> removed = new HashSet<String>();
        Registry.userHandlerMap.forEach((s, userHandler) -> {
            userHandler.getLastPing().addAndGet(5);
            if (userHandler.getLastPing().compareAndSet(20, 0)) {
                LOGGER.info("User is set to offline => {}", userHandler.getUser());
                userHandler.setOnline(false);
                removed.add(userHandler.getUser().getUsername());
                try {
                    userHandler.getDos().writeUTF("timeout");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        removed.forEach(s -> Registry.userHandlerMap.remove(s));
    }
}
