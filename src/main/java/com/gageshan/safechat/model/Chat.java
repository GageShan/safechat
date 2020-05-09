package com.gageshan.safechat.model;

import lombok.Data;

import java.util.Date;

/**
 * Create by gageshan on 2020/5/9 17:41
 */
@Data
public class Chat {
    private String id;
    private String sendUserId;
    private String receiveUserId;
    private String content;
    private Date time;
}
