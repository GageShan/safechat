package com.gageshan.safechat.mapper;

import com.gageshan.safechat.model.Chat;
import org.apache.ibatis.annotations.Mapper;

/**
 * Create by gageshan on 2020/5/9 17:44
 */
@Mapper
public interface ChatMapper {
    void insert(Chat chat);
}
