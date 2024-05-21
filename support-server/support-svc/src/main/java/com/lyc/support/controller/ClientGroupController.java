package com.lyc.support.controller;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.PageResponseVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.support.dto.ClientDTO;
import com.lyc.support.dto.ClientGroupDTO;
import com.lyc.support.dto.ClientGroupPageRespDTO;
import com.lyc.support.dto.ClientGroupReqDTO;
import com.lyc.support.service.ClientGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/4/10 9:05
 * @Description: 客户端组
 */
@Log4j2
@Api(value = "客户端组信息API", tags = {"客户端组信息CURD"})
@RestController
@RequestMapping("/clientGroup")
public class ClientGroupController {

    @Autowired
    ClientGroupService clientGroupService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/getPageList")
    public PageResponseVO<ClientGroupPageRespDTO> getPageList(@RequestBody PageRequestVO<ClientGroupDTO> page){

        Page<ClientGroupPageRespDTO> pageRespDTOS = clientGroupService.getPageList(page);
        return PageResponseVO.of(pageRespDTOS);
    }


    @ApiOperation(value = "全量查询")
    @PostMapping("/getAllList")
    public ResponseVO<List<ClientGroupDTO>> getAllList(@RequestBody ClientGroupReqDTO clientGroupReqDTO){

        return clientGroupService.getAllList(clientGroupReqDTO);
    }

    @ApiOperation(value = "保存信息", tags = {"保存信息CURD"})
    @PostMapping("/save")
    public ResponseVO<ClientGroupDTO> save(@RequestBody ClientGroupDTO clientGroupDTO){
        if(StringUtils.isBlank(clientGroupDTO.getId())){
            clientGroupService.save(clientGroupDTO);
        }else{
            clientGroupService.update(clientGroupDTO);
        }
        return ResponseVO.success(clientGroupDTO);
    }

}
