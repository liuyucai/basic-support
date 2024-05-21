package com.lyc.support.controller;

import com.lyc.common.vo.ResponseVO;
import com.lyc.support.dto.*;
import com.lyc.support.service.RoleResource1Service;
import com.lyc.support.service.RoleResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/9/19 8:47
 * @Description:
 */
@Log4j2
@Api(value = "角色资源信息API", tags = {"角色资源信息CURD"})
@RestController
@RequestMapping("/roleResource1")
public class RoleResource1Controller {

    @Autowired
    RoleResource1Service roleResource1Service;


    @ApiOperation(value = "获取客户端列表")
    @PostMapping("/getClientList")
    public ResponseVO<List<RoleClientRespDTO>> getClientList(@RequestBody RoleClientReqDTO roleClientReqDTO){

        return roleResource1Service.getClientList(roleClientReqDTO);
    }

    @ApiOperation(value = "获取菜单列表")
    @PostMapping("/getMenuList")
    public ResponseVO<List<RoleMenuRespDTO>> getMenuList(@RequestBody RoleResourceReqDTO roleResourceReqDTO){

        return roleResource1Service.getMenuList(roleResourceReqDTO);
    }

    @ApiOperation(value = "授予客户端权限")
    @PostMapping("/openClientAuth")
    public ResponseVO openClientAuth(@RequestBody RoleResourceOpenDTO roleResourceOpenDTO){
        return roleResource1Service.openClientAuth(roleResourceOpenDTO);
    }

    @ApiOperation(value = "关闭客户端权限")
    @DeleteMapping("/closeClientAuth")
    public ResponseVO closeClientAuth(@RequestParam(name = "id") String id){
        return roleResource1Service.closeClientAuth(id);
    }

    /**
     * 保存授权
     */

    @ApiOperation(value = "保存菜单授权")
    @PostMapping("/saveMenuAuth")
    public ResponseVO saveMenuAuth(@RequestBody RoleMenuResourcesDTO roleMenuResourcesDTO){
        return roleResource1Service.saveMenuAuth(roleMenuResourcesDTO);
    }
}
