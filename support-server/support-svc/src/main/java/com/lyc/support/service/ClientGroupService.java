package com.lyc.support.service;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.BaseService;
import com.lyc.support.dto.ClientGroupDTO;
import com.lyc.support.dto.ClientGroupPageRespDTO;
import com.lyc.support.dto.ClientGroupReqDTO;
import com.lyc.support.entity.ClientGroup;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/4/10 9:13
 * @Description:
 */
public interface ClientGroupService extends BaseService<ClientGroupDTO, ClientGroup,String> {
    Page<ClientGroupPageRespDTO> getPageList(PageRequestVO<ClientGroupDTO> page);

    ResponseVO<List<ClientGroupDTO>> getAllList(ClientGroupReqDTO clientGroupReqDTO);
}
