package com.lyc.common.vo;

/**
 * @author: liuyucai
 * @Created: 2023/2/22 9:13
 * @Description:
 */
public enum ResultCode {

    SUCCESS("0000", "成功"),
    FAIL("0001", "失败"),
    PERMISSION("0002", "没有访问权限"),
    SYSTEM_ERROR("9998", "系统内部异常"),
    PARAM_VALID_FAIL("9999", "参数校验失败");

    private String code;
    private String desc;

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    private ResultCode() {
    }

    private ResultCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
