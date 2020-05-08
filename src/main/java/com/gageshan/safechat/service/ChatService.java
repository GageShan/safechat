package com.gageshan.safechat.service;

import com.alibaba.fastjson.JSONObject;
import com.gageshan.safechat.enums.ChatType;
import com.gageshan.safechat.model.Group;
import com.gageshan.safechat.netty.UserRef;
import com.gageshan.safechat.utils.ResponseJson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;

/**
 * Create by gageshan on 2020/5/7 15:14
 */
@Service
public class ChatService {

    @Autowired
    private GroupService groupService;

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    public void removeChannel(ChannelHandlerContext ctx) {
        Iterator<Map.Entry<String, ChannelHandlerContext>> iterator = UserRef.onlineUserMap.entrySet().iterator();

        while(iterator.hasNext()) {
            Map.Entry<String, ChannelHandlerContext> next = iterator.next();
            if(next.getValue() == ctx) {
                logger.info("正在删除握手实例 : " + ctx.channel().id().asShortText());
                UserRef.webSocketHandshakerMap.remove(ctx.channel().id().asLongText());
                iterator.remove();
                return;
            }
        }
    }

    public void register(JSONObject jsonObject, ChannelHandlerContext ctx) {
        //
        String userId = (String) jsonObject.get("userId");
        UserRef.onlineUserMap.put(userId,ctx);
        String json = new ResponseJson().success()
                .setData("type", ChatType.REGISTER).toString();
        sendMessage(ctx,json);
        logger.info(userId + "已登录");
    }

    private void sendMessage(ChannelHandlerContext ctx, String json) {
        ctx.channel().writeAndFlush(new TextWebSocketFrame(json));
    }

    public void singleSend(JSONObject jsonObject, ChannelHandlerContext ctx) {
        String fromUserId = (String)jsonObject.get("fromUserId");
        String toUserId = (String)jsonObject.get("toUserId");
        String content = (String)jsonObject.get("content");
        ChannelHandlerContext toUserChannel = UserRef.onlineUserMap.get(toUserId);
        logger.info(UserRef.onlineUserMap.toString());
        if(toUserChannel == null) {
            String json = new ResponseJson().error(MessageFormat.format("userId为{0}的用户没登录",toUserId)).toString();
            sendMessage(ctx,json);
            logger.info(fromUserId + "发送给" + toUserId + "的信息失败");
        } else {
            String json = new ResponseJson().success()
                    .setData("fromUserId",fromUserId)
                    .setData("content",content)
                    .setData("type",ChatType.SINGLE_SENDING)
                    .toString();
            logger.info(fromUserId + "发送给" + toUserId + "的信息成功");
            sendMessage(ctx,json);
        }
    }

    public void groupSend(JSONObject jsonObject, ChannelHandlerContext ctx) {
        String fromUserId = (String)jsonObject.get("fromUserId");
        String toGroupId = (String)jsonObject.get("toGroupId");
        String content = (String)jsonObject.get("content");
        Group group = groupService.getGroup(toGroupId);

        if(group == null) {
            String json = new ResponseJson().error("该群id不存在").toString();
            sendMessage(ctx,json);
        } else {
            String json = new ResponseJson().success()
                    .setData("fromUserId",fromUserId)
                    .setData("content",content)
                    .setData("toGroupId",toGroupId)
                    .setData("type",ChatType.GROUP_SENDING)
                    .toString();
            group.getMembers().stream().forEach(member -> {
                ChannelHandlerContext toCtx = UserRef.onlineUserMap.get(member.getUserId());
                if(toCtx != null && !member.getUserId().equals(fromUserId)) {
                    sendMessage(toCtx,json);
                }
            });
        }
    }

    public void typeError(ChannelHandlerContext ctx) {
        String json = new ResponseJson()
                .error("该类型不存在").toString();
        sendMessage(ctx,json);
    }
}
