package com.gageshan.safechat.model;

import lombok.Data;

/**
 * Create by gageshan on 2020/5/6 17:27
 */
@Data
public class User {
    private String userId;
    private String username;
    private String password;
    private String avatarUrl;
}
