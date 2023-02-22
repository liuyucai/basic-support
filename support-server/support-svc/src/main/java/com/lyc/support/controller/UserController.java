package com.lyc.support.controller;

import com.lyc.support.entity.User;
import com.lyc.support.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/2/22 8:53
 * @Description:
 */
@Log4j2
@Api(value = "用户信息API", tags = {"用户信息CURD"})
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "获取用户信息", tags = {"用户信息CURD"})
    @PostMapping("/getUserInfo")
    public List<User> getUserInfo(){

        List<User> list =  userService.getUserInfo();
        return list;
    }

}
