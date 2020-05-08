package com.gageshan.safechat.enums;

/**
 * Create by gageshan on 2020/5/6 19:45
 */
public enum ConstantEnum {
    USER_TOKEN(1,"userId");

    public Integer TYPE;
    public String STATUS;

    ConstantEnum(Integer TYPE, String STATUS) {
        this.TYPE = TYPE;
        this.STATUS = STATUS;
    }
    public Integer getTYPE() {
        return TYPE;
    }
}
