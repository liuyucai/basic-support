package com.lyc.support.controller;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.PageResponseVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.security.utils.SecurityContextUtil;
import com.lyc.support.dto.*;
import com.lyc.support.service.ClientRouterService;
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
 * @Created: 2023/4/23 18:31
 * @Description:
 */
@Log4j2
@Api(value = "路由信息API", tags = {"路由信息CURD"})
@RestController
@RequestMapping("/clientRouter")
public class ClientRouterController {

    @Autowired
    ClientRouterService clientRouterService;

    @ApiOperation(value = "保存路由信息", tags = {"保存信息CURD"})
    @PostMapping("/saveRouter")
    public ResponseVO<RouterInfoDTO> saveRouter(@RequestBody RouterInfoDTO routerInfoDTO){

        if(StringUtils.isBlank(routerInfoDTO.getId())){
            return clientRouterService.saveRouterInfo(routerInfoDTO);
        }else{
            return clientRouterService.updateRouterInfo(routerInfoDTO);
        }
    }

    @ApiOperation(value = "保存功能信息", tags = {"保存信息CURD"})
    @PostMapping("/saveFunction")
    public ResponseVO<FunctionInfoDTO> saveFunction(@RequestBody FunctionInfoDTO functionInfoDTO){

        if(StringUtils.isBlank(functionInfoDTO.getPid())){
            ResponseVO.fail("路由id不能为空");
        }
        if(StringUtils.isBlank(functionInfoDTO.getId())){
            return clientRouterService.saveFunctionInfo(functionInfoDTO);
        }else{
            return clientRouterService.updateFunctionInfo(functionInfoDTO);
        }
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/getPageList")
    public PageResponseVO<RouterInfoPageRespDTO> getPageList(@RequestBody PageRequestVO<RouterInfoReqDTO> page){

        Page<RouterInfoPageRespDTO> pageRespDTOS = clientRouterService.getPageList(page);
        return PageResponseVO.of(pageRespDTOS);
    }

    @ApiOperation(value = "获取功能列表")
    @PostMapping("/getFunctionList")
    public ResponseVO<List<FunctionInfoDTO>> getFunctionList(@RequestBody FunctionInfoDTO functionInfoDTO){

        List<FunctionInfoDTO> functionList = clientRouterService.getFunctionList(functionInfoDTO);
        return ResponseVO.success(functionList);
    }

    /**
     * 删除路由信息
     */
    @ApiOperation(value = "删除路由信息", tags = {"删除信息CURD"})
    @DeleteMapping("/deleteRouter/{id}")
    public ResponseVO deleteRouter(
            @ApiParam(name = "id", value = "id") @PathVariable("id") String id){

        clientRouterService.deleteRouter(id);

        return ResponseVO.success(id);
    }

    /**
     * 删除功能信息
     */
    @ApiOperation(value = "删除功能信息", tags = {"删除信息CURD"})
    @DeleteMapping("/deleteFuction/{id}")
    public ResponseVO deleteFuction(
            @ApiParam(name = "id", value = "id") @PathVariable("id") String id){

        clientRouterService.delete(id);
        return ResponseVO.success(id);
    }

    @ApiOperation(value = "根据客户端id获取登录用户的路由功能权限信息")
    @PostMapping("/getByClientSecret")
    public ResponseVO<List<UserClientRouterDTO>> getByClientSecret(@RequestBody ClientRouterReqDTO clientRouterReqDTO){
        List<UserClientRouterDTO> list = clientRouterService.getByClientSecret(clientRouterReqDTO);
        return ResponseVO.success(list);
    }

}
