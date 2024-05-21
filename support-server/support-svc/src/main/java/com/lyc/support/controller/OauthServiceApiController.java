package com.lyc.support.controller;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.PageResponseVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.security.annotation.Auth;
import com.lyc.simple.dto.BaseDTO;
import com.lyc.support.dto.*;
import com.lyc.support.service.OauthServiceApiService;
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
 * @Created: 2023/7/30 12:37
 * @Description:
 */
@Log4j2
@Api(value = "服务API信息API", tags = {"服务信息CURD"})
@RestController
@RequestMapping("/serviceApi")
public class OauthServiceApiController {

    @Autowired
    OauthServiceApiService oauthServiceApiService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/getPageList")
    public PageResponseVO<OauthServiceApiDTO> getPageList(@RequestBody PageRequestVO<OauthServiceApiDTO> page){

        Page<OauthServiceApiDTO> pageRespDTOS = oauthServiceApiService.getPageList(page);
        return PageResponseVO.of(pageRespDTOS);
    }

    @ApiOperation(value = "保存信息", tags = {"保存信息CURD"})
    @PostMapping(value = "/save",name = "保存信息")
    public ResponseVO<BaseDTO> save(@RequestBody OauthServiceApiBaseDTO oauthServiceApiBaseDTO){

        if(StringUtils.isBlank(oauthServiceApiBaseDTO.getId())){
            return oauthServiceApiService.insertApi(oauthServiceApiBaseDTO);
        }else{
            return oauthServiceApiService.updateApi(oauthServiceApiBaseDTO);
        }
    }

    /**
     * 删除API信息
     */
    @ApiOperation(value = "删除API信息", tags = {"删除信息CURD"})
    @DeleteMapping("/delete/{id}")
    public ResponseVO deleteApi(
            @ApiParam(name = "id", value = "id") @PathVariable("id") String id){

        oauthServiceApiService.deleteApi(id);
        return ResponseVO.success(id);
    }

}
