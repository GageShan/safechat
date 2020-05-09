package com.gageshan.safechat.enums;

/**
 * Create by gageshan on 2020/5/7 15:56
 */
public enum ChatType {
    KEEPALIVE(1,"KEEPALIVE"),
    REGISTER(2,"REGISTER"),
    SINGLE_SENDING(3,"SINGLE_SENDING"),
    GROUP_SENDING(4,"GROUP_SENDING"),
    FILE_MSG_SINGLE_SENDING(5,"FILE_MSG_SINGLE_SENDING"),
    FILE_MSG_GROUP_SENDING(6,"FILE_MSG_GROUP_SENDING");

    public Integer TYPE;
    public String STATUS;

    ChatType(Integer TYPE, String STATUS) {
        this.TYPE = TYPE;
        this.STATUS = STATUS;
    }

    //    public static void main(String[] args) {
//        System.out.println(KEEPALIVE);
//    }
}
