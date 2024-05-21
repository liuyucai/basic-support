package com.lyc.support.controller;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.PageResponseVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.security.annotation.Auth;
import com.lyc.simple.dto.BaseDTO;
import com.lyc.support.dto.*;
import com.lyc.support.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/3/25 21:26
 * @Description:
 */
@Log4j2
@Api(value = "客户端信息API", tags = {"客户端信息CURD"})
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;


    @ApiOperation(value = "保存信息", tags = {"保存信息CURD"})
    @PostMapping(value = "/save",name = "保存信息")
    public ResponseVO<BaseDTO> save(@RequestBody ClientBaseDTO clientBaseDTO){


        if(StringUtils.isBlank(clientBaseDTO.getId())){
            clientService.insertClient(clientBaseDTO);
        }else{
            clientService.updateClient(clientBaseDTO);
        }
        return ResponseVO.success(clientBaseDTO);
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/getPageList")
    public PageResponseVO<ClientPageRespDTO> getPageList(@RequestBody PageRequestVO<ClientDTO> page){

        Page<ClientPageRespDTO> pageRespDTOS = clientService.getPageList(page);
        return PageResponseVO.of(pageRespDTOS);
    }

    @ApiOperation(value = "全量查询")
    @PostMapping(value="/getAllList",name="全量查询")
    public ResponseVO<List<ClientDTO>> getAllList(@RequestBody ClientReqDTO clientReqDTO){
        return clientService.getAllList(clientReqDTO);
    }

    @ApiOperation(value = "根据客户端密钥获取客户端信息")
    @GetMapping("/getByClientSecret")
    public ResponseVO<ClientDTO> getByClientSecret(@RequestParam("clientSecret") String clientSecret){

        return clientService.getByClientSecret(clientSecret);
    }
}
