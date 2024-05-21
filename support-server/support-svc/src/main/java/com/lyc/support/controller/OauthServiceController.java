package com.lyc.support.controller;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.PageResponseVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.support.dto.*;
import com.lyc.support.service.OauthServiceAuthService;
import com.lyc.support.service.OauthServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/4/14 8:57
 * @Description:
 */
@Log4j2
@Api(value = "服务信息API", tags = {"服务信息CURD"})
@RestController
@RequestMapping("/service")
public class OauthServiceController {

    @Autowired
    OauthServiceService oauthServiceService;

    @Autowired
    OauthServiceAuthService oauthServiceAuthService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/getPageList")
    public PageResponseVO<ServiceDTO> getPageList(@RequestBody PageRequestVO<ServiceDTO> page){

        Page<ServiceDTO> pageRespDTOS = oauthServiceService.getPageList(page);
        return PageResponseVO.of(pageRespDTOS);
    }

    @ApiOperation(value = "全量查询")
    @PostMapping("/getAllList")
    public ResponseVO<List<ServiceDTO>> getAllList(@RequestBody ServiceDTO serviceDTO){

        List<ServiceDTO> list = oauthServiceService.findAll(serviceDTO);
        return ResponseVO.success(list);
    }

    @ApiOperation(value = "保存信息", tags = {"保存信息CURD"})
    @PostMapping("/save")
    public ResponseVO<ServiceDTO> save(@RequestBody ServiceDTO serviceDTO){
        if(StringUtils.isBlank(serviceDTO.getId())){
            oauthServiceService.save(serviceDTO);
        }else{
            oauthServiceService.update(serviceDTO);
        }
        return ResponseVO.success(serviceDTO);
    }

    @ApiOperation(value = "分页查询服务的授权资源信息")
    @PostMapping("/getServiceAuthPageList")
    public PageResponseVO<ServiceAuthRespDTO> getServiceAuthPageList(@RequestBody PageRequestVO<ServiceAuthReqDTO> page){

        Page<ServiceAuthRespDTO> pageRespDTOS = oauthServiceService.getServiceAuthPageList(page);
        return PageResponseVO.of(pageRespDTOS);
    }


    @ApiOperation(value = "添加服务授权资源")
    @PostMapping("/saveServiceAuth")
    public ResponseVO<OauthServiceAuthDTO> saveServiceAuth(@RequestBody OauthServiceAuthDTO oauthServiceAuthDTO){

        return oauthServiceAuthService.saveServiceAuth(oauthServiceAuthDTO);
    }

    @ApiOperation(value = "删除服务授权资源")
    @DeleteMapping("/deleteServiceAuth")
    public ResponseVO deleteServiceAuth(@RequestParam(name = "id") String id){
        oauthServiceAuthService.delete(id);

        return ResponseVO.success();
    }
}
