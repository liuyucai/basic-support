package com.lyc.support.controller;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.PageResponseVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.support.dto.*;
import com.lyc.support.service.ClientService;
import com.lyc.support.service.OauthClientServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author: liuyucai
 * @Created: 2023/4/17 9:02
 * @Description:客户端服务信息API
 */
@Log4j2
@Api(value = "客户端服务信息API", tags = {"客户端服务信息CURD"})
@RestController
@RequestMapping("/clientService")
public class ClientServiceController {

    @Autowired
    OauthClientServiceService oauthClientServiceService;

    /**
     *
     * @param page
     * @return
     */
    @ApiOperation(value = "分页查询")
    @PostMapping("/getPageList")
    public PageResponseVO<ClientServiceRespDTO> getPageList(@RequestBody PageRequestVO<ClientServiceDTO> page){

        Page<ClientServiceRespDTO> pageRespDTOS = oauthClientServiceService.getPageList(page);
        return PageResponseVO.of(pageRespDTOS);
    }

    //添加
    @ApiOperation(value = "保存信息", tags = {"保存信息CURD"})
    @PostMapping("/save")
    public ResponseVO<ClientServiceDTO> save(@RequestBody ClientServiceDTO clientServiceDTO){

        return oauthClientServiceService.saveClientService(clientServiceDTO);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除信息", tags = {"删除信息CURD"})
    @DeleteMapping("/delete/{id}")
    public ResponseVO delete(
            @ApiParam(name = "id", value = "id") @PathVariable("id") String id){

        oauthClientServiceService.delete(id);

        return ResponseVO.success(id);
    }
}
