package com.lyc.support.controller;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.PageResponseVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.dto.BaseDTO;
import com.lyc.support.dto.*;
import com.lyc.support.service.OauthMenuApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author: liuyucai
 * @Created: 2023/11/1 8:53
 * @Description:
 */
@Log4j2
@Api(value = "菜单API信息API", tags = {"服务信息CURD"})
@RestController
@RequestMapping("/menuApi")
public class OauthMenuApiController {

    @Autowired
    OauthMenuApiService oauthMenuApiService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/getPageList")
    public PageResponseVO<OauthMenuApiRespDTO> getPageList(@RequestBody PageRequestVO<OauthMenuApiReqDTO> page){

        Page<OauthMenuApiRespDTO> pageRespDTOS = oauthMenuApiService.getPageList(page);
        return PageResponseVO.of(pageRespDTOS);
    }

    @ApiOperation(value = "授予API权限")
    @PostMapping("/openApiAuth")
    public ResponseVO openApiAuth(@RequestBody OauthMenuApiDTO oauthMenuApiDTO){
        return oauthMenuApiService.openApiAuth(oauthMenuApiDTO);
    }

    @ApiOperation(value = "关闭API权限")
    @DeleteMapping("/closeApiAuth")
    public ResponseVO closeApiAuth(@RequestParam(name = "id") String id){
        return oauthMenuApiService.closeApiAuth(id);
    }

    @ApiOperation(value = "分页获取所有的api信息")
    @PostMapping("/getAllPageList")
    public PageResponseVO<OauthMenuApiRespDTO> getAllPageList(@RequestBody PageRequestVO<OauthMenuApiReqDTO> page){

        Page<OauthMenuApiRespDTO> pageRespDTOS = oauthMenuApiService.getAllPageList(page);
        return PageResponseVO.of(pageRespDTOS);
    }


}
