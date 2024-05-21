package com.lyc.auth.service;

import com.lyc.auth.dto.ClientDTO;
import com.lyc.auth.entity.Client;
import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.BaseService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/3/25 21:26
 * @Description:
 */
public interface ClientService extends BaseService<ClientDTO, Client,String> {

    Client getByClientSecret(String clientSecret);

    String getIdByClientSecret(String clientSecret);
}
