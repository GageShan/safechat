package com.gageshan.safechat.netty;

import com.alibaba.fastjson.JSONObject;
import com.gageshan.safechat.enums.ChatType;
import com.gageshan.safechat.service.ChatService;
import com.gageshan.safechat.utils.ResponseJson;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Create by gageshan on 2020/5/6 21:29
 */
@Component
@ChannelHandler.Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServerHandler.class);

    @Autowired
    private ChatService chatService;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame) throws Exception {
        handlerWebSocketFrame(channelHandlerContext,webSocketFrame);
    }

    /**
     * 处理websocketframe
     * @param ctx
     * @param frame
     * @throws Exception
     */
    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        //关闭请求
        if(frame instanceof CloseWebSocketFrame) {
            WebSocketServerHandshaker webSocketServerHandshaker =
                    UserRef.webSocketHandshakerMap.get(ctx.channel().id().asLongText());
            if(webSocketServerHandshaker == null) {
                sendErrorMsg(ctx,"不存在的客户端连接");
            } else {
                webSocketServerHandshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            }
            return;
        }

        if(frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        if(!(frame instanceof TextWebSocketFrame)) {
            sendErrorMsg(ctx,"仅支持文本格式");
            return;
        }

        //客户端发送的文本消息
        String content = ((TextWebSocketFrame) frame).text();
        logger.info("服务器收到新消息：" + content);
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.parseObject(content);
        } catch (Exception e) {
            sendErrorMsg(ctx,"json字符串转换出错");
        }
        if(jsonObject == null) {
            sendErrorMsg(ctx,"参数为空");
            return;
        }
        String type = (String)jsonObject.get("type");
        if(ChatType.REGISTER.STATUS.equals(type)) {
            chatService.register(jsonObject,ctx);
            logger.info("登录" + UserRef.onlineUserMap.toString());
        } else if(ChatType.SINGLE_SENDING.STATUS.equals(type)) {
            chatService.singleSend(jsonObject,ctx);
        } else if(ChatType.GROUP_SENDING.STATUS.equals(type)) {
            chatService.groupSend(jsonObject,ctx);
        } else if(ChatType.FILE_MSG_SINGLE_SENDING.STATUS.equals(type)) {
            //文件上传
        } else if(ChatType.FILE_MSG_GROUP_SENDING.STATUS.equals(type)) {
            //文件群组上传
        } else if(ChatType.KEEPALIVE.STATUS.equals(type)){
            logger.info("收到来自channel为[" + ctx.channel().id().asShortText() + "]的心跳包...");
        } else {
            chatService.typeError(ctx);
        }
        //
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        chatService.removeChannel(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    private void sendErrorMsg(ChannelHandlerContext ctx, String errorMsg) {
        String responseJson = new ResponseJson().error(errorMsg).toString();
        ctx.writeAndFlush(new TextWebSocketFrame(responseJson));
    }
}
