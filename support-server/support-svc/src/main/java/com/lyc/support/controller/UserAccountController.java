package com.lyc.support.controller;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.PageResponseVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.support.dto.*;
import com.lyc.support.entity.UserAccount;
import com.lyc.support.service.UserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/2/22 8:53
 * @Description:
 */
@Log4j2
@Api(value = "账号信息API", tags = {"账号信息CURD"})
@RestController
@RequestMapping("/userAccount")
public class UserAccountController {

    @Autowired
    UserAccountService userAccountService;

    @ApiOperation(value = "获取账号信息", tags = {"用户信息CURD"})
    @PostMapping("/getUserInfo")
    public List<UserAccount> getUserInfo(){

        List<UserAccount> list =  userAccountService.getUserInfo();
        return list;
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/getPageList")
    public PageResponseVO<UserAccountPageRespDTO> getPageList(@RequestBody PageRequestVO<UserAccountPageReqDTO> page){

        Page<UserAccountPageRespDTO> pageRespDTOS = userAccountService.getPageList(page);
        return PageResponseVO.of(pageRespDTOS);
    }

    @ApiOperation(value = "保存信息", tags = {"保存信息CURD"})
    @PostMapping("/save")
    public ResponseVO<UserAccountSaveInfoDTO> save(@RequestBody UserAccountSaveInfoDTO userAccountSaveInfoDTO){

        if(StringUtils.isBlank(userAccountSaveInfoDTO.getId())){
            return userAccountService.saveUserInfo(userAccountSaveInfoDTO);
        }else{
            return userAccountService.updateUserInfo(userAccountSaveInfoDTO);
        }
    }

    /**
     * 删除账号信息
     */
    @ApiOperation(value = "删除账号信息")
    @DeleteMapping("/delete")
    public ResponseVO delete(@RequestParam(name = "id") String id){
        return userAccountService.deleteAccount(id);
    }

    /**
     * 获取用户详细信息
     */
    @ApiOperation(value = "获取用户详细信息", tags = {"获取用户详细信息CURD"})
    @GetMapping("/getDetailById/{id}")
    public ResponseVO<UserAccountDetailDTO> getUserDetailById(
            @ApiParam(name = "id", value = "id") @PathVariable("id") String id){
        return userAccountService.getUserDetailById(id);
    }

    @ApiOperation(value = "修改密码", tags = {"保存信息CURD"})
    @PatchMapping("/updatePassword")
    public ResponseVO updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO){
        return userAccountService.updatePassword(updatePasswordDTO);
    }

    @ApiOperation(value = "重置密码", tags = {"保存信息CURD"})
    @PatchMapping("/resetPassword")
    public ResponseVO resetPassword(@RequestParam(name = "id") String id){
        return userAccountService.resetPassword(id);
    }

    @ApiOperation(value = "绑定手机", tags = {"保存信息CURD"})
    @PatchMapping("/bindPhone")
    public ResponseVO bindPhone(@RequestBody BindPhoneDTO bindPhoneDTO){
        return userAccountService.bindPhone(bindPhoneDTO);
    }

    @ApiOperation(value = "解绑手机", tags = {"保存信息CURD"})
    @PatchMapping("/unbindPhone")
    public ResponseVO unbindPhone(@RequestParam(name = "id") String id){
        return userAccountService.unbindPhone(id);
    }

    @ApiOperation(value = "绑定邮箱", tags = {"保存信息CURD"})
    @PatchMapping("/bindEmail")
    public ResponseVO bindEmail(@RequestBody BindEmailDTO bindEmailDTO){
        return userAccountService.bindEmail(bindEmailDTO);
    }

    @ApiOperation(value = "解绑邮箱", tags = {"保存信息CURD"})
    @PatchMapping("/unbindEmail")
    public ResponseVO unbindEmail(@RequestParam(name = "id") String id){
        return userAccountService.unbindEmail(id);
    }


}
