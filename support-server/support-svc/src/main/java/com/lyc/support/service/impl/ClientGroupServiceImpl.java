package com.lyc.support.service.impl;

import com.lyc.common.vo.OrderVO;
import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.support.dto.ClientDTO;
import com.lyc.support.dto.ClientGroupDTO;
import com.lyc.support.dto.ClientGroupPageRespDTO;
import com.lyc.support.dto.ClientGroupReqDTO;
import com.lyc.support.entity.ClientGroup;
import com.lyc.support.entity.qo.ClientGroupPageQO;
import com.lyc.support.repository.ClientGroupRepository;
import com.lyc.support.repository.ClientRepository;
import com.lyc.support.service.ClientGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/4/10 9:13
 * @Description:
 */
@Service
public class ClientGroupServiceImpl extends SimpleServiceImpl<ClientGroupDTO, ClientGroup,String, ClientGroupRepository> implements ClientGroupService {


    @Override
    public Page<ClientGroupPageRespDTO> getPageList(PageRequestVO<ClientGroupDTO> page) {

        PageRequest pageRequest =  PageRequest.of(page.getPage() - 1, page.getSize());
        Page<ClientGroupPageRespDTO> p = this.complexFindPage(page.getCondition(),pageRequest, ClientGroupPageQO.class, ClientGroupPageRespDTO.class);


        for (ClientGroupPageRespDTO clientGroupPageRespDTO:p.getContent()) {
            if(clientGroupPageRespDTO.getClientNumber() == null){
                clientGroupPageRespDTO.setClientNumber(0);
            }
        }
        
        return p;
    }

    @Override
    public ResponseVO<List<ClientGroupDTO>> getAllList(ClientGroupReqDTO clientGroupReqDTO) {

        ClientGroupDTO clientGroupDTO = new ClientGroupDTO();

        BeanUtils.copyProperties(clientGroupReqDTO,clientGroupDTO);


        List<ClientGroupDTO> list = this.findAll(clientGroupDTO,ClientGroupDTO.class,clientGroupReqDTO.getSort());
        return ResponseVO.success(list);
    }
}
