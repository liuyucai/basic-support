package com.lyc.auth.enums;

/**
 * @author: liuyucai
 * @Created: 2023/6/2 16:02
 * @Description:
 */
public enum ResponseCodeEnum {

    GET_CAPTCHA_ERROR("5001","获取验证码错误"),
    CAPTCHA_ERROR("5002","验证码错误"),

    USER_ACCOUNT_ERROR("5003","用户账号错误"),
    PASSWORD_ERROR("5004","密码错误"),
    USER_ACCOUNT_DISABLED("5005","用户账号已被禁用"),
    USER_ACCOUNT_LOCK("5006","用户账号已被锁定"),

    ORG_USER_ERROR("5007","用户信息异常"),
    ORG_NOT_SELECT("5008","未选择用户机构"),
    ORG_USER_NOT_EXIST("5009","用户信息不存在"),
    ORG_USER_DISABLED("5010","用户已被禁用"),
    ORG_USER_NOT_AUTH("5011","用户未授权"),
    CLIENT_SECRET_ERROR("5012","客户端密钥错误"),

    ;

    private String code;

    private String desc;


    ResponseCodeEnum(String code,String desc){

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
