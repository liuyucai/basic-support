package com.lyc.support.service;

import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.BaseService;
import com.lyc.support.dto.ClientMenu1DTO;
import com.lyc.support.dto.ClientMenuReqDTO;
import com.lyc.support.dto.UserClientMenuDTO;
import com.lyc.support.dto.UserClientMenuReqDTO;
import com.lyc.support.entity.ClientMenu1;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/8/25 14:35
 * @Description:
 */
public interface ClientMenu1Service extends BaseService<ClientMenu1DTO, ClientMenu1,String> {
    ResponseVO<List<ClientMenu1DTO>> getAllList(ClientMenuReqDTO clientMenuReqDTO);

    ResponseVO<ClientMenu1DTO> saveClientMenu(ClientMenu1DTO clientMenuDTO);

    ResponseVO<ClientMenu1DTO> updateClientMenu(ClientMenu1DTO clientMenuDTO);

    List<UserClientMenuDTO> getByClientSecret(UserClientMenuReqDTO userClientMenuReqDTO);
}
