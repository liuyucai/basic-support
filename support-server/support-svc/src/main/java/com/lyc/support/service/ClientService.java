package com.lyc.support.service;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.BaseService;
import com.lyc.support.dto.*;
import com.lyc.support.entity.Client;
import com.lyc.support.entity.Org;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/3/25 21:26
 * @Description:
 */
public interface ClientService extends BaseService<ClientDTO, Client,String> {
    Page<ClientPageRespDTO> getPageList(PageRequestVO<ClientDTO> page);

    ClientDTO insertClient(ClientBaseDTO clientBaseDTO);

    ClientBaseDTO updateClient(ClientBaseDTO clientBaseDTO);

    ResponseVO<List<ClientDTO>> getAllList(ClientReqDTO clientReqDTO);

    ResponseVO<ClientDTO> getByClientSecret(String clientSecret);
}
