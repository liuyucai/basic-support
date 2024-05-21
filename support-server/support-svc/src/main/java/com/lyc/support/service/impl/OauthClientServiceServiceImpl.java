package com.lyc.support.service.impl;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.support.dto.ClientDTO;
import com.lyc.support.dto.ClientPageRespDTO;
import com.lyc.support.dto.ClientServiceDTO;
import com.lyc.support.dto.ClientServiceRespDTO;
import com.lyc.support.entity.OauthClientService;
import com.lyc.support.entity.qo.ClientPageQO;
import com.lyc.support.entity.qo.ClientServicePageQO;
import com.lyc.support.repository.ClientServiceRepository;
import com.lyc.support.service.OauthClientServiceService;
import com.lyc.support.sql.ClientServiceSqlConfig;
import com.lyc.support.sql.ClientSqlConfig;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: liuyucai
 * @Created: 2023/4/17 8:52
 * @Description:
 */
@Service
@Log4j2
public class OauthClientServiceServiceImpl extends SimpleServiceImpl<ClientServiceDTO, OauthClientService,String, ClientServiceRepository> implements OauthClientServiceService {


    @Autowired
    ClientServiceSqlConfig clientServiceSqlConfig;

    @Autowired
    ClientServiceRepository clientServiceRepository;

    @Override
    public Page<ClientServiceRespDTO> getPageList(PageRequestVO<ClientServiceDTO> page) {
        ClientServiceDTO condition = page.getCondition();
        StringBuilder sql = new StringBuilder(clientServiceSqlConfig.getQueryPageSql().getColumns());
        sql.append(clientServiceSqlConfig.getQueryPageSql().getTables());

        Map<String, Object> params = new HashMap<>();

        if(condition != null) {
            if (StringUtils.isNotBlank(condition.getClientId())) {
                sql.append(" and cs.client_id = :clientId ");
                params.put("clientId", condition.getClientId());
            }
        }
        sql.append(clientServiceSqlConfig.getQueryPageSql().getCondition());

        PageRequest pageRequest =  PageRequest.of(page.getPage() - 1, page.getSize());

        Page<ClientServiceRespDTO> p = this.nativeQueryByPage(
                sql.toString(),
                pageRequest,
                params,
                ClientServicePageQO.class,
                ClientServiceRespDTO.class);
        return p;
    }

    @Override
    public ResponseVO<ClientServiceDTO> saveClientService(ClientServiceDTO clientServiceDTO) {

        try{
            Integer number = clientServiceRepository.getClientServiceNumberByInfo(clientServiceDTO.getClientId(),clientServiceDTO.getServiceId());

            if(number.intValue() == 0){
                this.save(clientServiceDTO);
                return ResponseVO.success(clientServiceDTO);
            }else{
                return ResponseVO.fail(clientServiceDTO);
            }
        }catch (Exception e){
            log.error("保存客户端服务失败",e);
            return ResponseVO.fail(clientServiceDTO);
        }
    }
}
