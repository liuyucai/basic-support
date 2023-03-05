package com.lyc.support.controller;

import com.lyc.support.entity.User;
import com.lyc.support.service.OrgService;
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
 * @Created: 2023/3/5 18:31
 * @Description:
 */
@Log4j2
@Api(value = "机构信息API", tags = {"机构信息CURD"})
@RestController
@RequestMapping("/org")
public class OrgController {

    @Autowired
    OrgService orgService;

    @ApiOperation(value = "获取机构信息", tags = {"用户信息CURD"})
    @PostMapping("/getPage")
    public List<User> getUserInfo(){

//        List<User> list =  orgService.getUserInfo();
//        return list;
        return null;
    }
}
