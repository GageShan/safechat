package com.gageshan.safechat.model.dto;

import com.gageshan.safechat.model.Group;
import com.gageshan.safechat.model.User;
import lombok.Data;

import java.util.List;

/**
 * Create by gageshan on 2020/5/7 21:58
 */
@Data
public class UserDTO {
    private String userId;
    private String username;
    private String password;
    private String avatarUrl;
    private List<User> friendList;
    private List<Group> groupList;
}
