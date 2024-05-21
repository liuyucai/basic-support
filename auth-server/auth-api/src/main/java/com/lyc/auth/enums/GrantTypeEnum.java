package com.lyc.auth.enums;

/**
 * @author: liuyucai
 * @Created: 2023/5/31 8:48
 * @Description:
 */
public enum GrantTypeEnum {

    PASSWORD("password","密码模式"),

    IMPLICIT("implicit","隐藏式"),

    CLIENT_CREDENTIALS("client_credentials","客户端凭证"),

    AUTHORIZATION_CODE("authorization_code","授权码模式"),
    ;

    private String code;

    private String desc;


    GrantTypeEnum(String code,String desc){

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
