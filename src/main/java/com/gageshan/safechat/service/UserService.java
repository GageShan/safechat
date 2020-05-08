package com.gageshan.safechat.service;

import com.gageshan.safechat.mapper.UserMapper;
import com.gageshan.safechat.model.User;
import com.gageshan.safechat.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by gageshan on 2020/5/6 17:40
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User queryUserByUsername(String username) {

        return userMapper.queryUserByUsername(username);
    }

    public String insert(String username, String password) throws Exception{
        User user = new User();
        String userId = UserUtils.getUUID();
        user.setUserId(userId);
        user.setUsername(username);
        user.setPassword(UserUtils.getMD5Str(password));
        user.setAvatarUrl(UserUtils.getAvatarUrl());
        userMapper.insert(user);
        return userId;
    }

    public User queryUserByUserId(String userId) {
        return userMapper.queryUserByUserId(userId);
    }
}
