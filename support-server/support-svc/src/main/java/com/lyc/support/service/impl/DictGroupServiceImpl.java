package com.lyc.support.service.impl;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.simple.utils.StringUtils;
import com.lyc.support.dto.DictGroupDTO;
import com.lyc.support.dto.DictGroupPageRespDTO;
import com.lyc.support.dto.ServiceAuthRespDTO;
import com.lyc.support.entity.DictGroup;
import com.lyc.support.entity.qo.DictGroupPageRespQO;
import com.lyc.support.entity.qo.ServiceAuthRespQO;
import com.lyc.support.repository.DictGroupRepository;
import com.lyc.support.service.DictGroupService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author: liuyucai
 * @Created: 2023/9/3 16:58
 * @Description:
 */
@Service
@Log4j2
public class DictGroupServiceImpl extends SimpleServiceImpl<DictGroupDTO, DictGroup,String, DictGroupRepository> implements DictGroupService {


    @Autowired
    DictGroupRepository dictGroupRepository;

    @Override
    public Page<DictGroupPageRespDTO> getPageList(PageRequestVO<DictGroupDTO> page) {
        PageRequest pageRequest =  PageRequest.of(page.getPage() - 1, page.getSize());

        Page<DictGroupPageRespDTO> p = this.complexFindPage(page.getCondition(),pageRequest, DictGroupPageRespQO.class, DictGroupPageRespDTO.class);

        return p;
    }

    @Override
    public ResponseVO insertDictGroup(DictGroupDTO dictGroupDTO) {
        //先判断是否有同名的code   serviceId code

        Integer number = null;
        if(StringUtils.isNotBlank(dictGroupDTO.getServiceId())){
            number = dictGroupRepository.getCountByCodeAndServiceId(dictGroupDTO.getCode(),dictGroupDTO.getServiceId());
        }else{
            number = dictGroupRepository.getCountByCode(dictGroupDTO.getCode());

        }

        if(number== null || number.intValue() == 0){
            this.save(dictGroupDTO);
            return ResponseVO.success(dictGroupDTO);
        }else{
            return ResponseVO.fail();
        }
    }

    @Override
    public ResponseVO updateDictGroup(DictGroupDTO dictGroupDTO) {
        //先判断是否有同名的code

        Integer number = null;
        if(StringUtils.isNotBlank(dictGroupDTO.getServiceId())){
            number = dictGroupRepository.getCountByCodeAndServiceId(dictGroupDTO.getId(),dictGroupDTO.getCode(),dictGroupDTO.getServiceId());
        }else{
            number = dictGroupRepository.getCountByCode(dictGroupDTO.getId(),dictGroupDTO.getCode());
        }

        if(number== null || number.intValue() == 0){
            this.update(dictGroupDTO);
            return ResponseVO.success(dictGroupDTO);
        }else{
            return ResponseVO.fail();
        }
    }
}
