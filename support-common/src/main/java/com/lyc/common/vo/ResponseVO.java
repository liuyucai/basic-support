package com.lyc.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/2/22 9:10
 * @Description:
 */
@Data
public class ResponseVO<T> implements Serializable {

    private String resultCode;

    private String resultDesc;

    private T data;

    private Integer status = 200;

    public static <T> ResponseVO<T> success(T data) {
        ResponseVO<T> result = new ResponseVO();
        result.data = data;
        result.setResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getDesc());
        return result;
    }

    public static <T> ResponseVO<T> success() {
        ResponseVO<T> result = new ResponseVO();
        result.setResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getDesc());
        return result;
    }

    public static <T> ResponseVO<T> fail(T data) {
        ResponseVO<T> result = new ResponseVO();
        result.data = data;
        result.setResult(ResultCode.FAIL.getCode(), ResultCode.FAIL.getDesc());
        return result;
    }

    public static <T> ResponseVO<T> fail() {
        ResponseVO<T> result = new ResponseVO();
        result.setResult(ResultCode.FAIL.getCode(), ResultCode.FAIL.getDesc());
        return result;
    }

    public static <T> ResponseVO<T> fail(String desc) {
        ResponseVO<T> result = new ResponseVO();
        result.setResult(ResultCode.FAIL.getCode(), desc);
        return result;
    }

    public static <T> ResponseVO<T> fail(String code,String desc) {
        ResponseVO<T> result = new ResponseVO();
        result.setResult(code, desc);
        return result;
    }

    public void setResult(String resultCode, String resultDesc) {
        this.resultCode = resultCode;
        this.resultDesc = resultDesc;
    }
}
