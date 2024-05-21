package com.lyc.support.service;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.simple.jpa.BaseService;
import com.lyc.support.dto.ServiceAuthReqDTO;
import com.lyc.support.dto.ServiceAuthRespDTO;
import com.lyc.support.dto.ServiceDTO;
import com.lyc.support.entity.OauthService;
import org.springframework.data.domain.Page;

/**
 * @author: liuyucai
 * @Created: 2023/4/14 8:58
 * @Description:
 */
public interface OauthServiceService extends BaseService<ServiceDTO, OauthService,String> {
    Page<ServiceDTO> getPageList(PageRequestVO<ServiceDTO> page);

    Page<ServiceAuthRespDTO> getServiceAuthPageList(PageRequestVO<ServiceAuthReqDTO> page);
}
