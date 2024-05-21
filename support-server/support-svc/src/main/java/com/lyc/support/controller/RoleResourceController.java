package com.lyc.support.controller;

import com.lyc.common.vo.ResponseVO;
import com.lyc.support.dto.*;
import com.lyc.support.service.RoleResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/5/13 15:23
 * @Description:
 */
@Log4j2
@Api(value = "角色资源信息1API", tags = {"角色资源信息1CURD"})
@RestController
@RequestMapping("/roleResource")
public class RoleResourceController {

    @Autowired
    RoleResourceService roleResourceService;


    @ApiOperation(value = "获取客户端列表")
    @PostMapping("/getClientList")
    public ResponseVO<List<RoleClientRespDTO>> getClientList(@RequestBody RoleClientReqDTO roleClientReqDTO){

        return roleResourceService.getClientList(roleClientReqDTO);
    }

    @ApiOperation(value = "获取菜单列表")
    @PostMapping("/getMenuList")
    public ResponseVO<List<RoleMenuRespDTO>> getMenuList(@RequestBody RoleResourceReqDTO roleResourceReqDTO){

        return roleResourceService.getMenuList(roleResourceReqDTO);
    }

    @ApiOperation(value = "获取路由列表")
    @PostMapping("/getRouterList")
    public ResponseVO<List<RoleRouterRespDTO>> getRouterList(@RequestBody RoleResourceReqDTO roleResourceReqDTO){

        return roleResourceService.getRouterList(roleResourceReqDTO);
    }

    @ApiOperation(value = "获取功能列表")
    @PostMapping("/getFunctionList")
    public ResponseVO<List<RoleFunctionRespDTO>> getFunctionList(@RequestBody RoleFunctionReqDTO roleFunctionReqDTO){
        return roleResourceService.getFunctionList(roleFunctionReqDTO);
    }

    @ApiOperation(value = "授予客户端权限")
    @PostMapping("/openClientAuth")
    public ResponseVO openClientAuth(@RequestBody RoleResourceOpenDTO roleResourceOpenDTO){
        return roleResourceService.openClientAuth(roleResourceOpenDTO);
    }

    @ApiOperation(value = "关闭客户端权限")
    @DeleteMapping("/closeClientAuth")
    public ResponseVO closeClientAuth(@RequestParam(name = "id") String id){
        return roleResourceService.closeClientAuth(id);
    }


    @ApiOperation(value = "授予菜单权限")
    @PostMapping("/openMenuAuth")
    public ResponseVO openMenuAuth(@RequestBody RoleResourceOpenDTO roleResourceOpenDTO){
        return roleResourceService.openMenuAuth(roleResourceOpenDTO);
    }

    @ApiOperation(value = "关闭菜单权限")
    @DeleteMapping("/closeMenuAuth")
    public ResponseVO closeMenuAuth(@RequestParam(name = "id") String id){
        return roleResourceService.closeMenuAuth(id);
    }

    @ApiOperation(value = "授予路由权限")
    @PostMapping("/openRouterAuth")
    public ResponseVO openRouterAuth(@RequestBody RoleResourceOpenDTO roleResourceOpenDTO){
        return roleResourceService.openRouterAuth(roleResourceOpenDTO);
    }

    @ApiOperation(value = "关闭路由权限")
    @DeleteMapping("/closeRouterAuth")
    public ResponseVO closeRouterAuth(@RequestParam(name = "id") String id){
        return roleResourceService.closeRouterAuth(id);
    }

    @ApiOperation(value = "授予功能权限")
    @PostMapping("/openFunctionAuth")
    public ResponseVO openFunctionAuth(@RequestBody RoleResourceOpenDTO roleResourceOpenDTO){
        return roleResourceService.openFunctionAuth(roleResourceOpenDTO);
    }

    @ApiOperation(value = "关闭功能权限")
    @DeleteMapping("/closeFunctionAuth")
    public ResponseVO closeFunctionAuth(@RequestParam(name = "id") String id){
        return roleResourceService.closeFunctionAuth(id);
    }
}
