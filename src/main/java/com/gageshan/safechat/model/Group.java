package com.gageshan.safechat.model;

import lombok.Data;

import java.util.List;

/**
 * Create by gageshan on 2020/5/7 0:07
 */
@Data
public class Group {
    private String groupId;
    private String groupName;
    private String groupAvatarUrl;
    private List<User> members;
}
