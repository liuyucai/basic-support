package com.lyc.support.controller;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.PageResponseVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.support.dto.*;
import com.lyc.support.entity.Role;
import com.lyc.support.service.RoleService;
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
 * @Created: 2023/4/29 13:53
 * @Description:
 */
@Log4j2
@Api(value = "角色信息API", tags = {"角色信息CURD"})
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @ApiOperation(value = "保存信息", tags = {"角色信息CURD"})
    @PostMapping("/save")
    public ResponseVO<RoleDTO> save(@RequestBody RoleDTO roleDTO){

        if(StringUtils.isBlank(roleDTO.getId())){
            roleService.save(roleDTO);
        }else{
            roleService.update(roleDTO);
        }
        return ResponseVO.success(roleDTO);
    }

    @ApiOperation(value = "分页查询",tags = {"角色信息CURD"})
    @PostMapping("/getPageList")
    public PageResponseVO<RolePageRespDTO> getPageList(@RequestBody PageRequestVO<RoleDTO> page){

        Page<RolePageRespDTO> pageRespDTOS = roleService.getPageList(page);
        return PageResponseVO.of(pageRespDTOS);
    }

    @ApiOperation(value = "全量查询",tags = {"角色信息CURD"})
    @PostMapping("/getAllList")
    public ResponseVO<List<RoleDTO>> getPageList(@RequestBody RoleDTO roleDTO){

        List<RoleDTO> list = roleService.findAll(roleDTO);
        return ResponseVO.success(list);
    }
}
