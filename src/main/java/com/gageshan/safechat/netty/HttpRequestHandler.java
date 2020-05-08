package com.gageshan.safechat.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Component;

/**
 * Create by gageshan on 2020/5/6 22:44
 */
@Component
@ChannelHandler.Sharable
public class HttpRequestHandler extends SimpleChannelInboundHandler<Object> {

    /**
     * 处理http
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FullHttpRequest) {
            handlerHttpRequest(ctx,(FullHttpRequest)msg);
        } else if(msg instanceof WebSocketFrame) {
            ctx.fireChannelRead(((WebSocketFrame)msg).retain());
        }
    }

    /**
     * 将http协议升级为websocket协议
     * @param ctx
     * @param request
     */
    private void handlerHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        if(!request.decoderResult().isSuccess()) {
            sendHttpResponse(ctx,request,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory webSocketServerHandshakerFactory = new WebSocketServerHandshakerFactory(
                "ws:/" + ctx.channel() + "/websocket",null,false
        );

        WebSocketServerHandshaker serverHandshaker = webSocketServerHandshakerFactory.newHandshaker(request);
        UserRef.webSocketHandshakerMap.put(ctx.channel().id().asLongText(),serverHandshaker);
        if(serverHandshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            serverHandshaker.handshake(ctx.channel(),request);
        }

    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, DefaultFullHttpResponse response) {

        //返回应当给客户端
        if(response.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(response.status().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(buf);
            buf.release();
        }
        boolean keepAlive = HttpUtil.isKeepAlive(request);
        ChannelFuture channelFuture = ctx.channel().writeAndFlush(response);

        if(!keepAlive) {
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
