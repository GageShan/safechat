package com.gageshan.safechat.mapper;

import com.gageshan.safechat.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Create by gageshan on 2020/5/6 18:08
 */

@Mapper
public interface UserMapper {
    User queryUserByUsername(String username);

    void insert(User user);

    User queryUserByUserId(String userId);
}
