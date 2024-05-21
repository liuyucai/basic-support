package com.lyc.support.controller;

import com.lyc.common.vo.ResponseVO;
import com.lyc.support.dto.*;
import com.lyc.support.service.ClientMenu1Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/8/25 14:38
 * @Description:
 */
@Log4j2
@Api(value = "菜单信息1API", tags = {"菜单信息1CURD"})
@RestController
@RequestMapping("/clientMenu1")
public class ClientMenu1Controller {

    @Autowired
    ClientMenu1Service clientMenuService;

    @ApiOperation(value = "全量查询")
    @PostMapping("/getAllList")
    public ResponseVO<List<ClientMenu1DTO>> getPageList(@RequestBody ClientMenuReqDTO clientMenuReqDTO){

        return clientMenuService.getAllList(clientMenuReqDTO);
    }

    @ApiOperation(value = "保存信息", tags = {"保存信息CURD"})
    @PostMapping("/save")
    public ResponseVO<ClientMenu1DTO> save(@RequestBody ClientMenu1DTO clientMenuDTO){

        if(StringUtils.isBlank(clientMenuDTO.getPid())){
            clientMenuDTO.setPid("0");
        }

        //
        if("DIR".equals(clientMenuDTO.getMenuType())){
            clientMenuDTO.setPath(null);
            clientMenuDTO.setPermission(null);
            clientMenuDTO.setAuthentication(null);
            clientMenuDTO.setKeepAlive(null);
            clientMenuDTO.setAction(null);
        }else if("ROUTER".equals(clientMenuDTO.getMenuType())){
            clientMenuDTO.setAction(null);
            clientMenuDTO.setVisiable(null);
        }else if("FUNCTION".equals(clientMenuDTO.getMenuType())){
            clientMenuDTO.setPath(null);
            clientMenuDTO.setKeepAlive(null);
            clientMenuDTO.setAction(null);
            clientMenuDTO.setVisiable(null);
        }

        if(StringUtils.isBlank(clientMenuDTO.getId())){
            return clientMenuService.saveClientMenu(clientMenuDTO);
        }else{
            return clientMenuService.updateClientMenu(clientMenuDTO);
        }
    }

//    @ApiOperation(value = "获取用户的客户端菜单")
//    @PostMapping("/getMenu")
//    public ResponseVO<List<UserClientMenuDTO>> getMenu(@RequestBody ClientMenuReqDTO clientMenuReqDTO){
//
//        return clientMenuService.getMenu(clientMenuReqDTO);
//    }

    @ApiOperation(value = "根据客户端id获取登录用户的菜单权限信息")
    @PostMapping("/getByClientSecret")
    public ResponseVO<List<UserClientMenuDTO>> getByClientSecret(@RequestBody UserClientMenuReqDTO userClientMenuReqDTO){
        List<UserClientMenuDTO> list = clientMenuService.getByClientSecret(userClientMenuReqDTO);
        return ResponseVO.success(list);
    }
}
