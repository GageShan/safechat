package com.gageshan.safechat.controller;

import com.gageshan.safechat.enums.ConstantEnum;
import com.gageshan.safechat.model.User;
import com.gageshan.safechat.service.UserService;
import com.gageshan.safechat.utils.ResponseJson;
import com.gageshan.safechat.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Create by gageshan on 2020/5/6 17:24
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/login","/"})
    public String index() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseJson login(HttpSession httpSession,
                        @RequestParam String username,
                        @RequestParam String password) throws Exception{
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return new ResponseJson().error("用户名或密码不能为空");
        }
        User user = userService.queryUserByUsername(username);

        if(user == null) {
            //注册
            String userId = userService.insert(username,password);
            httpSession.setAttribute(ConstantEnum.USER_TOKEN.STATUS,userId);

        } else {
            //登录
            if(UserUtils.getMD5Str(password).equals(user.getPassword())) {
                httpSession.setAttribute(ConstantEnum.USER_TOKEN.STATUS,user.getUserId());
            } else {
                return new ResponseJson().error("密码不正确");
            }
        }
        return new ResponseJson().success();
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseJson logout(HttpSession session) {
        String userToken = ConstantEnum.USER_TOKEN.STATUS;
        String userId = (String)session.getAttribute(userToken);
        if(userId == null) {
            return new ResponseJson().error("请先登录");
        }
        session.removeAttribute(userId);
        return new ResponseJson().success();
    }
}
