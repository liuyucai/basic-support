package com.lyc.support.service;

import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.BaseService;
import com.lyc.support.dto.ClientMenuDTO;
import com.lyc.support.dto.ClientMenuReqDTO;
import com.lyc.support.dto.UserClientMenuDTO;
import com.lyc.support.entity.ClientMenu;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/4/20 16:36
 * @Description:
 */
public interface ClientMenuService extends BaseService<ClientMenuDTO, ClientMenu,String> {
    ResponseVO<List<ClientMenuDTO>> getAllList(ClientMenuReqDTO clientMenuReqDTO);

    ResponseVO<ClientMenuDTO> saveClientMenu(ClientMenuDTO clientMenuDTO);

    ResponseVO<ClientMenuDTO> updateClientMenu(ClientMenuDTO clientMenuDTO);

    ResponseVO<List<UserClientMenuDTO>> getMenu(ClientMenuReqDTO clientMenuReqDTO);
}
