package com.gageshan.safechat.service;

import com.gageshan.safechat.mapper.MyFriendsMapper;
import com.gageshan.safechat.model.MyFriends;
import com.gageshan.safechat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by gageshan on 2020/5/7 22:18
 */
@Service
public class MyFriendsService {
    @Autowired
    private MyFriendsMapper myFriendsMapper;

    public List<User> queryFriendsByUserId(String userId) {
        return myFriendsMapper.queryFriendsByUserId(userId);
    }

    public void insert(String id, String userId, String friendId) {
        myFriendsMapper.insert(id,userId,friendId);
    }

    public boolean isFriend(String userId, String friendId) {
        MyFriends myFriends = myFriendsMapper.isFriend(userId,friendId);
        if(myFriends == null) {
            return false;
        }
        return true;
    }
}
