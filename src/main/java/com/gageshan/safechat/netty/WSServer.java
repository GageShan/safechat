package com.gageshan.safechat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Create by gageshan on 2020/5/6 20:37
 */
@Component
public class WSServer {


    private final Logger logger = LoggerFactory.getLogger(WSServer.class);
//    private static class SingletionWSServer {
//        static final WSServer instance = new WSServer();
//    }

//    public static WSServer getInstance() {
//        return SingletionWSServer.instance;
//    }

    @Autowired
    private EventLoopGroup bossGroup;
    @Autowired
    private EventLoopGroup workerGroup;
    @Autowired
    private ServerBootstrap serverBootstrap;
    @Autowired
    private WebSocketChildChannelHandler webSocketChildChannelHandler;
    @Value("${webSocketPort}")
    private Integer PORT;

    public WSServer() {
//        bossGroup = new NioEventLoopGroup();
//        workerGroup = new NioEventLoopGroup();
//        serverBootstrap = new ServerBootstrap();

//        serverBootstrap.group(bossGroup,workerGroup)
//                .channel(NioServerSocketChannel.class)
//                .childHandler(webSocketChildChannelHandler);
    }

    public void start() throws Exception{
        serverBootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(webSocketChildChannelHandler);

        ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();
        if (channelFuture.isSuccess()) {
            logger.info("netty服务器启动");
        } else {
            logger.info("netty服务器启动失败");
        }

    }
}
