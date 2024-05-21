package com.lyc.auth.util;

import com.lyc.auth.dto.AccountLoginDTO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.common.vo.ResultCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/7/25 9:56
 * @Description:
 */
@Data
public class LoginResponseVO<T> extends ResponseVO<T> implements Serializable {


    /**
     *  客户端id
     */
    private String clientId;

    /**
     *  机构用户id
     */
    private String orgUserId;

    public static <T> LoginResponseVO<T> success(T data) {
        LoginResponseVO result = new LoginResponseVO();
        result.setData(data);
        result.setResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getDesc());
        return result;
    }

    public static <T> LoginResponseVO<T> success() {
        LoginResponseVO result = new LoginResponseVO();
        result.setResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getDesc());
        return result;
    }

    public static <T> LoginResponseVO<T> fail(T data) {
        LoginResponseVO result = new LoginResponseVO();
        result.setData(data);
        result.setResult(ResultCode.FAIL.getCode(), ResultCode.FAIL.getDesc());
        return result;
    }

    public static <T> LoginResponseVO<T> fail() {
        LoginResponseVO result = new LoginResponseVO();
        result.setResult(ResultCode.FAIL.getCode(), ResultCode.FAIL.getDesc());
        return result;
    }

    public static <T> LoginResponseVO<T> fail(String desc) {
        LoginResponseVO result = new LoginResponseVO();
        result.setResult(ResultCode.FAIL.getCode(), desc);
        return result;
    }

    public static <T> LoginResponseVO<T> fail(String code,String desc) {
        LoginResponseVO result = new LoginResponseVO();
        result.setResult(code, desc);
        return result;
    }

    @Override
    public void setResult(String resultCode, String resultDesc) {
        this.setResultCode(resultCode);
        this.setResultDesc(resultDesc);
    }

}
