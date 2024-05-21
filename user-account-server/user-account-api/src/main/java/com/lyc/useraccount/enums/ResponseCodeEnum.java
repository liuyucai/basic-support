package com.lyc.useraccount.enums;

/**
 * @author: liuyucai
 * @Created: 2023/6/2 16:02
 * @Description:
 */
public enum ResponseCodeEnum {

    GET_TOKEN_ERROR("6001","获取token失败"),
    TOKEN_ERROR("6002","token错误"),

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
