package com.lyc.support.service.impl;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.dto.BaseDTO;
import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.simple.utils.StringUtils;
import com.lyc.support.dto.DictItemDTO;
import com.lyc.support.entity.DictItem;
import com.lyc.support.repository.DictItemRepository;
import com.lyc.support.service.DictItemService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author: liuyucai
 * @Created: 2023/9/3 17:00
 * @Description:
 */
@Service
@Log4j2
public class DictItemServiceImpl extends SimpleServiceImpl<DictItemDTO, DictItem,String, DictItemRepository> implements DictItemService {

    @Autowired
    DictItemRepository dictItemRepository;

    @Override
    public Page<DictItemDTO> getDictItemPageList(PageRequestVO<DictItemDTO> page) {

        PageRequest pageRequest =  PageRequest.of(page.getPage() - 1, page.getSize());

        return this.findPage(page.getCondition(),pageRequest);
    }

    @Override
    public ResponseVO<BaseDTO> insertDictItem(DictItemDTO dictItemDTO) {

        //先获取该下是否有该code
        Integer number = dictItemRepository.getCountByCode(dictItemDTO.getGroupId(),dictItemDTO.getCode());;

        if(number== null || number.intValue() == 0){
            this.save(dictItemDTO);
            return ResponseVO.success(dictItemDTO);
        }else{
            return ResponseVO.fail();
        }
    }

    @Override
    public ResponseVO<BaseDTO> updateDictItem(DictItemDTO dictItemDTO) {
        //先获取该下是否有该code
        Integer number = dictItemRepository.getCountByCode(dictItemDTO.getId(),dictItemDTO.getGroupId(),dictItemDTO.getCode());;

        if(number== null || number.intValue() == 0){
            this.update(dictItemDTO);
            return ResponseVO.success(dictItemDTO);
        }else{
            return ResponseVO.fail();
        }
    }
}
