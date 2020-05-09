package com.gageshan.safechat.mapper;

import com.gageshan.safechat.model.MyFriends;
import com.gageshan.safechat.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Create by gageshan on 2020/5/7 22:17
 */
@Mapper
public interface MyFriendsMapper {
    List<User> queryFriendsByUserId(String userId);

    void insert(String id, String userId, String friendId);

    MyFriends isFriend(String userId, String friendId);
}
