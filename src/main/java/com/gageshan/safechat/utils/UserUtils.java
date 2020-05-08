package com.gageshan.safechat.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;

/**
 * Create by gageshan on 2020/5/6 18:44
 */
public class UserUtils {
    public static String getMD5Str(String strValue) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest(strValue.getBytes()));
    }
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static String getAvatarUrl() {
        return String.format("http://images.nowcoder.com/head/%dm.png",new Random().nextInt(1000));
    }
}
