package com.lyc.support.service;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.BaseService;
import com.lyc.support.dto.ClientServiceDTO;
import com.lyc.support.dto.ClientServiceRespDTO;
import com.lyc.support.entity.OauthClientService;
import org.springframework.data.domain.Page;

/**
 * @author: liuyucai
 * @Created: 2023/4/17 8:52
 * @Description:
 */
public interface OauthClientServiceService extends BaseService<ClientServiceDTO, OauthClientService,String> {
    Page<ClientServiceRespDTO> getPageList(PageRequestVO<ClientServiceDTO> page);

    ResponseVO<ClientServiceDTO> saveClientService(ClientServiceDTO clientServiceDTO);
}
