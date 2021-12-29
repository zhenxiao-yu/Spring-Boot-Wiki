package com.zhenxiao.wiki.exception;

public enum BusinessExceptionCode {

    USER_LOGIN_NAME_EXIST("Username already exists"),
    LOGIN_USER_ERROR("User does not exist or password is incorrect"),
    VOTE_REPEAT("You already liked this"),
    ;

    private String desc;

    BusinessExceptionCode(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
