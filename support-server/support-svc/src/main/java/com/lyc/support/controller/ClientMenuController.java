package com.lyc.support.controller;

import com.lyc.common.vo.ResponseVO;
import com.lyc.support.dto.*;
import com.lyc.support.service.ClientMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/4/20 16:39
 * @Description:
 */
@Log4j2
@Api(value = "菜单信息API", tags = {"菜单信息CURD"})
@RestController
@RequestMapping("/clientMenu")
public class ClientMenuController {

    @Autowired
    ClientMenuService clientMenuService;

    @ApiOperation(value = "全量查询")
    @PostMapping("/getAllList")
    public ResponseVO<List<ClientMenuDTO>> getPageList(@RequestBody ClientMenuReqDTO clientMenuReqDTO){

        return clientMenuService.getAllList(clientMenuReqDTO);
    }

    @ApiOperation(value = "保存信息", tags = {"保存信息CURD"})
    @PostMapping("/save")
    public ResponseVO<ClientMenuDTO> save(@RequestBody ClientMenuDTO clientMenuDTO){

        if(StringUtils.isBlank(clientMenuDTO.getPid())){
            clientMenuDTO.setPid("0");
        }
        if(StringUtils.isBlank(clientMenuDTO.getId())){
            return clientMenuService.saveClientMenu(clientMenuDTO);
        }else{
            return clientMenuService.updateClientMenu(clientMenuDTO);
        }
    }

    @ApiOperation(value = "获取用户的客户端菜单")
    @PostMapping("/getMenu")
    public ResponseVO<List<UserClientMenuDTO>> getMenu(@RequestBody ClientMenuReqDTO clientMenuReqDTO){

        return clientMenuService.getMenu(clientMenuReqDTO);
    }

}
