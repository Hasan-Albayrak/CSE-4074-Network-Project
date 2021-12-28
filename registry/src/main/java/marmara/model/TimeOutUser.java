package marmara.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeOutUser implements Runnable {
    private static Logger LOGGER = LoggerFactory.getLogger(TimeOutUser.class);

    @Override
    public void run() {
        while (true) {
            try {

                Thread.sleep(100000);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
            Registry.userHandlerMap.forEach((s, userHandler) -> {
                userHandler.getLastPing().addAndGet(60);
                if (userHandler.getLastPing().compareAndSet(200, 0)) {
                    userHandler.setOnline(false);
                }
            });
        }
    }
}
