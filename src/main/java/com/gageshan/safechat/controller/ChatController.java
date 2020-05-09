package com.gageshan.safechat.controller;

import com.gageshan.safechat.enums.ConstantEnum;
import com.gageshan.safechat.model.User;
import com.gageshan.safechat.model.dto.UserDTO;
import com.gageshan.safechat.netty.UserRef;
import com.gageshan.safechat.netty.WSServer;
import com.gageshan.safechat.service.MyFriendsService;
import com.gageshan.safechat.service.UserService;
import com.gageshan.safechat.utils.ResponseJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Create by gageshan on 2020/5/6 20:23
 */
@Controller
public class ChatController {
    private final Logger logger = LoggerFactory.getLogger(ChatController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private MyFriendsService myFriendsService;

    @GetMapping("/chatroom")
    public String chat() {
        return "chatroom";
    }

    @PostMapping("/chatroom/get_userinfo")
    @ResponseBody
    public ResponseJson getUserInfo(HttpSession httpSession) {
        String userId = (String) httpSession.getAttribute(ConstantEnum.USER_TOKEN.STATUS);
        User user = userService.queryUserByUserId(userId);
        if(user == null) {
            return new ResponseJson().error("用户尚未登录");
        }
        UserDTO userDTO = new UserDTO();

        BeanUtils.copyProperties(user,userDTO);
        userDTO.setFriendList(myFriendsService.queryFriendsByUserId(userId));
        logger.info(userDTO.toString());
        return new ResponseJson().success().setData("userInfo",userDTO);
    }
}
