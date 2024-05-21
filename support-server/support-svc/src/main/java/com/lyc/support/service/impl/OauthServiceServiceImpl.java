package com.lyc.support.service.impl;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.simple.utils.SortUtils;
import com.lyc.support.dto.ClientGroupPageRespDTO;
import com.lyc.support.dto.ServiceAuthReqDTO;
import com.lyc.support.dto.ServiceAuthRespDTO;
import com.lyc.support.dto.ServiceDTO;
import com.lyc.support.entity.OauthService;
import com.lyc.support.entity.qo.ClientGroupPageQO;
import com.lyc.support.entity.qo.ServiceAuthRespQO;
import com.lyc.support.repository.ServiceRepository;
import com.lyc.support.service.OauthServiceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author: liuyucai
 * @Created: 2023/4/14 8:58
 * @Description:
 */
@Service
@Log4j2
public class OauthServiceServiceImpl extends SimpleServiceImpl<ServiceDTO, OauthService,String, ServiceRepository> implements OauthServiceService {
    @Override
    public Page<ServiceDTO> getPageList(PageRequestVO<ServiceDTO> page) {

        PageRequest pageRequest =  PageRequest.of(page.getPage() - 1, page.getSize());

        return this.findPage(page.getCondition(),pageRequest);
    }

    @Override
    public Page<ServiceAuthRespDTO> getServiceAuthPageList(PageRequestVO<ServiceAuthReqDTO> page) {

        PageRequest pageRequest =  PageRequest.of(page.getPage() - 1, page.getSize());

        Page<ServiceAuthRespDTO> p = this.complexFindPage(page.getCondition(),pageRequest, ServiceAuthRespQO.class, ServiceAuthRespDTO.class);

        return p;
    }
}
