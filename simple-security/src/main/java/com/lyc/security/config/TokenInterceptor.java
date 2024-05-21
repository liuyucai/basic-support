package com.lyc.security.config;

import com.lyc.common.utils.StringUtils;
import com.lyc.security.utils.SecurityContextUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: liuyucai
 * @Created: 2023/6/12 9:48
 * @Description:
 */
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        String token = request.getHeader("access-token");
        if(StringUtils.isNotBlank(token)){
            SecurityContextUtil.addToken(token);
        }
        return true;
    }
}
