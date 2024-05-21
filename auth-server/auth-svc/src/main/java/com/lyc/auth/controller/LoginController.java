package com.lyc.auth.controller;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.lyc.auth.dto.AccountLoginDTO;
import com.lyc.auth.service.LoginService;
import com.lyc.auth.util.LoginResponseVO;
import com.lyc.common.utils.StringUtils;
import com.lyc.common.vo.ResponseVO;
import com.lyc.security.dto.UserDetailDTO;
import com.lyc.security.utils.SecurityContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: liuyucai
 * @Created: 2023/3/12 10:45
 * @Description:
 */
@Log4j2
@Api(value = "登录信息API", tags = {"登录信息CURD"})
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @ApiOperation(value = "账号密码登录", tags = {"登录信息CURD"})
    @PostMapping("/accountLogin")
    public ResponseVO accountLogin(@RequestBody AccountLoginDTO accountLoginDTO,HttpServletRequest httpServletRequest){

        //记录登录信息:  登录状态，登录ip,机构用户id、用户昵称、操作系统、浏览器、访问时间、登录信息

        //记录密码失败次数：账号、次数，
        //返回时，格式化参数

        LoginResponseVO loginResponseVO = (LoginResponseVO) loginService.accountLogin(accountLoginDTO,httpServletRequest);

        ResponseVO responseVO = loginResponseVO;
        return responseVO;
    }


    /**
     * 退出登录
     *
     * 获取用户的accessToken,解析，并删除redis的accessToken
     */

    @ApiOperation(value = "退出登录", tags = {"登录信息CURD"})
    @GetMapping("/logout")
    public ResponseVO logout(HttpServletRequest httpServletRequest){

        return loginService.logout(httpServletRequest);
    }


    /**
     * 切换用户
     *
     * 用户id
     */

    @ApiOperation(value = "切换用户", tags = {"登录信息CURD"})
    @GetMapping("/changeUser")
    public ResponseVO changeUser(
            HttpServletRequest httpServletRequest,
            @RequestParam(value="clientId",required = false) String clientId,
            @RequestParam(value="userId",required = true) String userId){

//        return loginService.logout(httpServletRequest);
        return loginService.changeUser(httpServletRequest,clientId,userId);
    }

}
