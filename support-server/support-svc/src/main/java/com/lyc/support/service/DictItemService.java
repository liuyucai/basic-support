package com.lyc.support.service;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.dto.BaseDTO;
import com.lyc.simple.jpa.BaseService;
import com.lyc.support.dto.DictItemDTO;
import com.lyc.support.entity.DictItem;
import org.springframework.data.domain.Page;

/**
 * @author: liuyucai
 * @Created: 2023/9/3 17:00
 * @Description:
 */
public interface DictItemService extends BaseService<DictItemDTO, DictItem,String> {
    Page<DictItemDTO> getDictItemPageList(PageRequestVO<DictItemDTO> page);

    ResponseVO<BaseDTO> insertDictItem(DictItemDTO dictItemDTO);

    ResponseVO<BaseDTO> updateDictItem(DictItemDTO dictItemDTO);
}
