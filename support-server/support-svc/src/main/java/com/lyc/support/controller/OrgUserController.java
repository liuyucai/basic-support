package com.lyc.support.controller;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.PageResponseVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.support.dto.*;
import com.lyc.support.service.OrgUserService;
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
 * @Created: 2023/5/16 17:03
 * @Description:
 */

@Log4j2
@Api(value = "用户信息API", tags = {"用户信息CURD"})
@RestController
@RequestMapping("/user")
public class OrgUserController {


    @Autowired
    OrgUserService orgUserService;

    @ApiOperation(value = "保存信息", tags = {"用户信息CURD"})
    @PostMapping("/save")
    public ResponseVO<OrgUserSaveDTO> save(@RequestBody OrgUserSaveDTO orgUserSaveDTO){

        if(StringUtils.isBlank(orgUserSaveDTO.getId())){
            return orgUserService.saveOrgUser(orgUserSaveDTO);
        }else{
            return orgUserService.updateOrgUser(orgUserSaveDTO);
        }
    }

    @ApiOperation(value = "分页查询",tags = {"用户信息CURD"})
    @PostMapping("/getPageList")
    public PageResponseVO<OrgUserPageRespDTO> getPageList(@RequestBody PageRequestVO<OrgUserPageReqDTO> page){

        Page<OrgUserPageRespDTO> pageRespDTOS = orgUserService.getPageList(page);
        return PageResponseVO.of(pageRespDTOS);
    }

    @ApiOperation(value = "全量查询",tags = {"用户信息CURD"})
    @PostMapping("/getAllList")
    public ResponseVO<List<OrgUserDTO>> getAllList(OrgUserDTO orgUserDTO){

        return orgUserService.getAllList(orgUserDTO);
    }

    /**
     * 根据户id,获取详情
     */
    @ApiOperation(value = "根据户id,获取详情", tags = {"用户信息CURD"})
    @GetMapping("/getDetailById/{id}")
    public ResponseVO<OrgUserDetailDTO> getDetailById(@ApiParam(name = "id", value = "id") @PathVariable("id") String id){
        return orgUserService.getDetailById(id);
    }


    /**
     * 根据户id,获取角色信息
     */
    @ApiOperation(value = "根据户id,获取角色信息", tags = {"用户信息CURD"})
    @PostMapping("/getRoleSettingPageList")
    public PageResponseVO<OrgUserRolePageRespDTO> getRoleSetting(@RequestBody PageRequestVO<OrgUserRolePageReqDTO> page){

        Page<OrgUserRolePageRespDTO> pageRespDTOS = orgUserService.getRoleSetting(page);
        return PageResponseVO.of(pageRespDTOS);
    }


    /**
     * 获取登录用户信息详情
     */
    @ApiOperation(value = "获取登录用户信息详情", tags = {"用户信息CURD"})
    @GetMapping("/getLoginUserDetail")
    public ResponseVO<LoginUserDetailDTO> getLoginUserDetail(){
        return orgUserService.getLoginUserDetail();
    }

    /**
     * 删除用户信息
     */
    @ApiOperation(value = "删除用户信息", tags = {"用户信息CURD"})
    @DeleteMapping("/delete/{id}")
    public ResponseVO delete(
            @ApiParam(name = "id", value = "id") @PathVariable("id") String id){
        return orgUserService.deleteUser(id);
    }

    /**
     * 更新用户类型  patch
     */
    @ApiOperation(value = "更新用户类型", tags = {"用户信息CURD"})
    @PatchMapping("/updateUserType")
    public ResponseVO updateUserType(@RequestBody UpdateOrgUserTypeDTO updateOrgUserTypeDTO){
        return orgUserService.updateUserType(updateOrgUserTypeDTO);
    }


    /**
     * 获取该账号拥有该客户端权限的用户， 账号id,客户端id,用户类型
     */
    @ApiOperation(value = "获取该账号拥有该客户端权限的用户",tags = {"用户信息CURD"})
    @PostMapping("/getClientAuthUserList")
    public ResponseVO<List<OrgUserDTO>> getClientAuthUserList(@RequestBody ClientAuthOrgUserReqDTO clientAuthOrgUserReqDTO){
        //  用户、角色、权限，获取用户后，一个一个去获取每个用户的权限进行遍历？   用户  -> 角色   机构
        return orgUserService.getClientAuthUserList(clientAuthOrgUserReqDTO);
    }
}
