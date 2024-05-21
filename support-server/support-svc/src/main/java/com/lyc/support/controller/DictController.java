package com.lyc.support.controller;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.PageResponseVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.dto.BaseDTO;
import com.lyc.support.dto.*;
import com.lyc.support.service.DictGroupService;
import com.lyc.support.service.DictItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author: liuyucai
 * @Created: 2023/9/3 17:02
 * @Description:
 */
@Log4j2
@Api(value = "字典信息API", tags = {"字典信息CURD"})
@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    DictGroupService dictGroupService;

    @Autowired
    DictItemService dictItemService;

    @ApiOperation(value = "字典组分页查询")
    @PostMapping("/getPageList")
    public PageResponseVO<DictGroupPageRespDTO> getPageList(@RequestBody PageRequestVO<DictGroupDTO> page){

        Page<DictGroupPageRespDTO> pageRespDTOS = dictGroupService.getPageList(page);
        return PageResponseVO.of(pageRespDTOS);
    }

    @ApiOperation(value = "保存字典组信息", tags = {"字典信息CURD"})
    @PostMapping(value = "/saveDictGroup")
    public ResponseVO<BaseDTO> saveDictGroup(@RequestBody DictGroupDTO dictGroupDTO){
        if(StringUtils.isBlank(dictGroupDTO.getId())){
            return dictGroupService.insertDictGroup(dictGroupDTO);
        }else{
            return dictGroupService.updateDictGroup(dictGroupDTO);
        }
    }

    @ApiOperation(value = "删除字典组")
    @DeleteMapping("/deleteDictGroup")
    public ResponseVO deleteDictGroup(@RequestParam(name = "id") String id){
        dictGroupService.delete(id);
        return ResponseVO.success();
    }


    @ApiOperation(value = "字典项分页查询")
    @PostMapping("/getDictItemPageList")
    public PageResponseVO<DictItemDTO> getDictItemPageList(@RequestBody PageRequestVO<DictItemDTO> page){

        Page<DictItemDTO> pageRespDTOS = dictItemService.getDictItemPageList(page);
        return PageResponseVO.of(pageRespDTOS);
    }

    @ApiOperation(value = "保存字典项信息", tags = {"字典信息CURD"})
    @PostMapping(value = "/saveDictItem")
    public ResponseVO<BaseDTO> saveDictItem(@RequestBody DictItemDTO dictItemDTO){
        if(StringUtils.isBlank(dictItemDTO.getId())){
            return dictItemService.insertDictItem(dictItemDTO);
        }else{
            return dictItemService.updateDictItem(dictItemDTO);
        }
    }


    @ApiOperation(value = "删除字典项")
    @DeleteMapping("/deleteDictItem")
    public ResponseVO deleteDictItem(@RequestParam(name = "id") String id){
        dictItemService.delete(id);
        return ResponseVO.success();
    }


}
