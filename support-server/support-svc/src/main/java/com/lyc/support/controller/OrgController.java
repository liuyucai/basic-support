package com.lyc.support.controller;

import com.lyc.common.vo.ResponseVO;
import com.lyc.support.dto.OrgDTO;
import com.lyc.support.dto.OrgReqDTO;
import com.lyc.support.service.OrgService;
import com.lyc.support.service.OrgUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/3/5 18:31
 * @Description:
 */
@Log4j2
@Api(value = "机构信息API", tags = {"机构信息CURD"})
@RestController
@RequestMapping("/org")
public class OrgController {

    @Autowired
    OrgService orgService;

    @Autowired
    OrgUserService orgUserService;


    @ApiOperation(value = "新增机构信息", tags = {"机构信息CURD"})
    @PostMapping("/save")
    public ResponseVO<OrgDTO> save(@RequestBody OrgDTO orgDTO){
        if(StringUtils.isBlank(orgDTO.getId())){
            return orgService.insertOrg(orgDTO);
        }else{
            return orgService.updateOrg(orgDTO);
        }
    }

    @ApiOperation(value = "获取机构信息", tags = {"机构信息CURD"})
    @PostMapping("/getAllList")
    public ResponseVO<List<OrgDTO>> getAllList(@RequestBody OrgReqDTO orgReqDTO){

        List<OrgDTO> list =  orgService.getAllList(orgReqDTO);
        return ResponseVO.success(list);
    }

    @ApiOperation(value = "删除机构信息", tags = {"机构信息CURD"})
    @DeleteMapping("/deleteById")
    public ResponseVO<List<OrgDTO>> findAll(@RequestParam(name = "id") String id){

        orgService.delete(id);
        return ResponseVO.success();
    }

    //根据用户id,获取该用户的机构信息
    @ApiOperation(value = "根据用户id,获取该用户的机构信息", tags = {"机构信息CURD"})
    @PostMapping("/getOrgListByUserId")
    public ResponseVO<List<OrgDTO>> getOrgListByUserId(@RequestParam(name = "userId") String userId){

        List<OrgDTO> list =  orgService.getOrgListByUserId(userId);
        return ResponseVO.success(list);
    }
}
