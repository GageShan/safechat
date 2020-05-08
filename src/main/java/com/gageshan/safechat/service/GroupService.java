package com.gageshan.safechat.service;

import com.gageshan.safechat.model.Group;
import com.gageshan.safechat.netty.UserRef;
import org.springframework.stereotype.Service;

/**
 * Create by gageshan on 2020/5/7 17:07
 */
@Service
public class GroupService {

    /**
     * 根据群id返回群组
     * @param groupId
     * @return
     */
    public Group getGroup(String groupId) {
        return UserRef.groupMap.get(groupId);
    }
}
