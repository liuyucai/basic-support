package com.lyc.support.emun;

/**
 * @author: liuyucai
 * @Created: 2022/5/16 14:50
 * @Description: 告警等级
 */
public enum ResourceTypeEnum {

    CLIENT("CLIENT","客户端"),
    MENU("MENU","菜单"),
    ROUTER("ROUTER","路由"),
    FUNCTION("FUNCTION","功能"),
    ;
    private String code;

    private String desc;

    ResourceTypeEnum(String code, String desc){
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
