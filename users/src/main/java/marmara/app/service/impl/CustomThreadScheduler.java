package marmara.app.service.impl;

import java.util.concurrent.ScheduledThreadPoolExecutor;


public class CustomThreadScheduler extends ScheduledThreadPoolExecutor {


    public CustomThreadScheduler(int corePoolSize) {
        super(corePoolSize);
    }

}


