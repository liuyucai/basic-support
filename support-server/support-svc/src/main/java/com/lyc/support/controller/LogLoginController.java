package com.lyc.support.controller;

import com.lyc.auth.dto.LogLoginDTO;
import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.PageResponseVO;
import com.lyc.support.dto.LogLoginReqDTO;
import com.lyc.support.dto.LogLoginRespDTO;
import com.lyc.support.service.LogLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: liuyucai
 * @Created: 2023/7/27 10:05
 * @Description:
 */
@Log4j2
@Api(value = "登录日志API", tags = {"登录日志CURD"})
@RestController
@RequestMapping("/logLogin")
public class LogLoginController {

    @Autowired
    LogLoginService logLoginService;

    /**
     *
     * @param page
     * @return
     */
    @ApiOperation(value = "分页查询")
    @PostMapping("/getPageList")
    public PageResponseVO<LogLoginRespDTO> getPageList(@RequestBody PageRequestVO<LogLoginReqDTO> page){

        Page<LogLoginRespDTO> pageRespDTOS = logLoginService.getPageList(page);
        return PageResponseVO.of(pageRespDTOS);
    }

}
