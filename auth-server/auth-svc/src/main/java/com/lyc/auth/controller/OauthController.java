package com.lyc.auth.controller;

import com.lyc.auth.dto.Oauth2DTO;
import com.lyc.auth.enums.GrantTypeEnum;
import com.lyc.common.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: liuyucai
 * @Created: 2023/5/29 11:19
 * @Description:
 */
@Log4j2
@Api(value = "鉴权API")
@RestController
@RequestMapping("/oauth")
public class OauthController {

    @ApiOperation(value = "Oauth2鉴权", tags = {"Oauth2鉴权CURD"})
    @RequestMapping("/authorize")
    public ResponseVO authorize(@RequestBody Oauth2DTO oauth2DTO){


        //refresh token 中携带过期时间
        if(GrantTypeEnum.PASSWORD.getCode().equals(oauth2DTO.getGrant_type())){

        }

        return null;
    }



}
