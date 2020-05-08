package com.gageshan.safechat.netty;

import com.gageshan.safechat.model.Group;
import com.gageshan.safechat.model.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create by gageshan on 2020/5/7 0:04
 */
public class UserRef {

    public static Map<String, WebSocketServerHandshaker> webSocketHandshakerMap =
            new ConcurrentHashMap<>();

    public static Map<String, ChannelHandlerContext> onlineUserMap =
            new ConcurrentHashMap<>();

    public static Map<String, Group> groupMap =
            new ConcurrentHashMap<>();

    public static Map<String, User> userMap =
            new HashMap<>();

}
