package com.gageshan.safechat.config;

import com.gageshan.safechat.netty.WebSocketChildChannelHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create by gageshan on 2020/5/8 22:18
 */
@Configuration
public class SpringBean {

    @Bean(name = "bossGroup")
    public EventLoopGroup bossGroup() {
        return new NioEventLoopGroup();
    }
    @Bean(name = "workerGroup")
    public EventLoopGroup workerGroup() {
        return new NioEventLoopGroup();
    }
    @Bean(name = "serverBootstrap")
    public ServerBootstrap serverBootstrap() {
        return new ServerBootstrap();
    }
//    @Bean(name = "webSocketChildChannelHandler")
//    public WebSocketChildChannelHandler webSocketChildChannelHandler() {
//        return new WebSocketChildChannelHandler();
//    }
}
