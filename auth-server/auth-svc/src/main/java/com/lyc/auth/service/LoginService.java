package com.lyc.auth.service;

import com.lyc.auth.dto.AccountLoginDTO;
import com.lyc.auth.util.LoginResponseVO;
import com.lyc.common.vo.ResponseVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: liuyucai
 * @Created: 2023/6/2 16:12
 * @Description:
 */
public interface LoginService {
    LoginResponseVO accountLogin(AccountLoginDTO accountLoginDTO, HttpServletRequest httpServletRequest);

    ResponseVO logout(HttpServletRequest httpServletRequest);

    ResponseVO changeUser(HttpServletRequest httpServletRequest,String clientId,String userId);
}
