package com.lyc.support.emun;

/**
 * @author: liuyucai
 * @Created: 2023/6/2 16:02
 * @Description:
 */
public enum ResponseCodeEnum {

    USER_NAME_EXIST_ERROR("4001","账号已存在"),
    PHONE_NO_EXIST_ERROR("4002","手机号已存在"),
    IDENTITY_NO_EXIST_ERROR("4003","证件号已存在"),
    EMAIL_EXIST_ERROR("4004","邮箱已存在"),
    USER_NAME_EMPTY_ERROR("4005","账号不能为空"),
    PHONE_NO_EMPTY_ERROR("4006","手机号不能为空"),
    EMAIL_EMPTY_ERROR("4007","邮箱不能为空"),

    API_EXIST_ERROR("5001","api已存在"),


    ;

    private String code;

    private String desc;


    ResponseCodeEnum(String code, String desc){

        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
