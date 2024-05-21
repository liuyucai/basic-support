package com.lyc.support.service;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.BaseService;
import com.lyc.support.dto.DictGroupDTO;
import com.lyc.support.dto.DictGroupPageRespDTO;
import com.lyc.support.entity.DictGroup;
import org.springframework.data.domain.Page;

/**
 * @author: liuyucai
 * @Created: 2023/9/3 16:58
 * @Description:
 */
public interface DictGroupService extends BaseService<DictGroupDTO, DictGroup,String> {
    Page<DictGroupPageRespDTO> getPageList(PageRequestVO<DictGroupDTO> page);

    ResponseVO insertDictGroup(DictGroupDTO dictGroupDTO);

    ResponseVO updateDictGroup(DictGroupDTO dictGroupDTO);
}
