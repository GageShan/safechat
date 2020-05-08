package com.gageshan.safechat;

import com.gageshan.safechat.netty.WSServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Create by gageshan on 2020/5/6 20:48
 */
@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private WSServer wsServer;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(contextRefreshedEvent.getApplicationContext().getParent() == null) {
            try {
                wsServer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
