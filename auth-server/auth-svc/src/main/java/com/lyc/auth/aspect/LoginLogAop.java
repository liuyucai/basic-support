package com.lyc.auth.aspect;

import com.alibaba.fastjson.JSONObject;
import com.lyc.auth.dto.AccountLoginDTO;
import com.lyc.auth.dto.LogLoginDTO;
import com.lyc.auth.service.LogLoginService;
import com.lyc.auth.util.LoginResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/7/24 10:16
 * @Description:
 */
@Aspect
@Component
@Slf4j
public class LoginLogAop {


    @Autowired
    LogLoginService logLoginService;

    @Pointcut("@annotation(com.lyc.auth.annotation.LoginLogAnno)")
    public void pointcut() {
    }

    @AfterReturning(value = "pointcut()", returning = "returnValue")
    public void afterReturning(JoinPoint joinPoint, Object returnValue) {

        //1024*1024*1024
        Object[] objs = joinPoint.getArgs();

        AccountLoginDTO accountLoginDTO = (AccountLoginDTO) objs[0];

        HttpServletRequest httpServletRequest = (HttpServletRequest) objs[1];

        LoginResponseVO loginResponseVO = (LoginResponseVO) returnValue;

        //获取浏览器类型

        String  userAgent  =   httpServletRequest.getHeader("User-Agent").toLowerCase();

        String lowerUserAgent = userAgent.toLowerCase();

        String os = "";
        //获取系统
        if(lowerUserAgent.indexOf("windows")>=0){
            String u1 =userAgent.substring(lowerUserAgent.indexOf("windows"));
            os = u1.substring(0,u1.indexOf(";"));
        }else if(userAgent.toLowerCase().indexOf("mac") >= 0){
            String u1 =userAgent.substring(lowerUserAgent.indexOf("mac"));
            os = u1.substring(0,u1.indexOf(";"));
        } else if(userAgent.toLowerCase().indexOf("x11") >= 0){
            os = "Unix";
        } else if(userAgent.toLowerCase().indexOf("android") >= 0){
            String u1 =userAgent.substring(lowerUserAgent.indexOf("android"));
            os = u1.substring(0,u1.indexOf(";"));
        } else if(userAgent.toLowerCase().indexOf("iphone") >= 0){
            String u1 =userAgent.substring(lowerUserAgent.indexOf("iphone"));
            os = u1.substring(0,u1.indexOf(";"));
        }

        //获取浏览器类型
        String browser = "";
        if(lowerUserAgent.indexOf("edg")>=0){
            String u1 =userAgent.substring(lowerUserAgent.indexOf("edg"));
            browser = u1.split(" ")[0];
        }else if(lowerUserAgent.indexOf("chrome")>=0){
            String u1 =userAgent.substring(lowerUserAgent.indexOf("chrome"));
            browser = u1.split(" ")[0];
        }else if(lowerUserAgent.indexOf("firefox")>=0){
            String u1 =userAgent.substring(lowerUserAgent.indexOf("firefox"));
            browser = u1.split(" ")[0];
        }else if(lowerUserAgent.indexOf("rv")>=0){
            String version =userAgent.substring(lowerUserAgent.indexOf("rv:"));
            version = version.split(" ")[0];
            version.replace(")","");
            version = version.split(":")[1];
            browser = "IE/"+version;
        }

        String ip = httpServletRequest.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getRemoteAddr();
            if("127.0.0.1".equals(ip)||"0:0:0:0:0:0:0:1".equals(ip)){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip= inet.getHostAddress();
            }
        }

        //获取ip地址
//        String host = httpServletRequest.getHeader("host");

        LogLoginDTO logLoginDTO = new LogLoginDTO();

        logLoginDTO.setUserName(accountLoginDTO.getUserName());
        logLoginDTO.setBrowser(browser);
        logLoginDTO.setOs(os);
        logLoginDTO.setIpAddress(ip);
        logLoginDTO.setClientId(loginResponseVO.getClientId());
        logLoginDTO.setOrgUserId("");

        String loginInfo = JSONObject.toJSONString(accountLoginDTO);
        if(loginInfo.length()>255){
            loginInfo = loginInfo.substring(0,255);
        }
        logLoginDTO.setLoginInfo(loginInfo);

        logLoginDTO.setLoginTime(new Date());
        logLoginDTO.setStatus(loginResponseVO.getResultCode());
        logLoginDTO.setMsg(loginResponseVO.getResultDesc());
        logLoginDTO.setOrgUserId(loginResponseVO.getOrgUserId());
        //登录的客户端信息
        logLoginService.save(logLoginDTO);

    }
}
