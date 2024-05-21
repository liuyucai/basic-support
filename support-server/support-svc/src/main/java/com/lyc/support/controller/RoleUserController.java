package com.lyc.support.controller;

import com.lyc.common.vo.ResponseVO;
import com.lyc.support.dto.OrgUserSaveDTO;
import com.lyc.support.dto.RoleDisabledDTO;
import com.lyc.support.service.RoleDisabledService;
import com.lyc.support.service.RoleUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: liuyucai
 * @Created: 2023/5/28 15:26
 * @Description:
 */
@Log4j2
@Api(value = "用户角色信息API", tags = {"用户角色信息CURD"})
@RestController
@RequestMapping("/roleUser")
public class RoleUserController {

    @Autowired
    RoleUserService roleUserService;

    @Autowired
    RoleDisabledService roleDisabledService;

    @ApiOperation(value = "关闭默认权限", tags = {"用户角色信息CURD"})
    @PostMapping("/closeDefaultRole")
    public ResponseVO closeDefaultRole(@RequestBody RoleDisabledDTO roleDisabledDTO){

        roleDisabledService.save(roleDisabledDTO);

        return ResponseVO.success();

    }


    @ApiOperation(value = "打开默认权限", tags = {"用户角色信息CURD"})
    @DeleteMapping("/openDefaultRole")
    public ResponseVO openDefaultRole(@RequestParam(name = "disabledId") String disabledId){

        roleDisabledService.delete(disabledId);

        return ResponseVO.success();

    }

    @ApiOperation(value = "删除权限", tags = {"用户角色信息CURD"})
    @DeleteMapping("/delete")
    public ResponseVO delete(@RequestParam(name = "id") String id){

        roleUserService.delete(id);

        return ResponseVO.success();

    }

}
