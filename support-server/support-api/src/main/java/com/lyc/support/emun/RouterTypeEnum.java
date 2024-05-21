package com.lyc.support.emun;

/**
 * @author: liuyucai
 * @Created: 2022/5/16 14:50
 * @Description: 告警等级
 */
public enum RouterTypeEnum {

    ROUTER("ROUTER","路由"),
    FUNCTION("FUNCTION","功能"),
    ;
    private String code;

    private String desc;

    RouterTypeEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    };

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
