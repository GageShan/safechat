package com.gageshan.safechat.netty;

import com.gageshan.safechat.service.ChatService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Create by gageshan on 2020/5/10 1:35
 */
@Component
@ChannelHandler.Sharable
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HeartBeatHandler.class);

    @Autowired
    private ChatService chatService;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent)evt;

            if(idleStateEvent.state() == IdleState.READER_IDLE) {
                //读空闲
            } else if(idleStateEvent.state() == IdleState.WRITER_IDLE) {
                //写空闲
            } else if(idleStateEvent.state() == IdleState.ALL_IDLE) {
                //读写空闲 ， 关闭相应的channel
                logger.info("触发心跳机制，删除" +ctx.channel().id().asShortText());
                chatService.removeChannel(ctx);
            }
        }
    }
}
