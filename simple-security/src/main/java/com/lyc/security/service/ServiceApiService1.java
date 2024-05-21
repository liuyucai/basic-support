package com.lyc.security.service;

import com.lyc.security.dto.ServiceApi1DTO;
import com.lyc.security.entity.OauthClient1;
import com.lyc.security.entity.ServiceApi1;
import com.lyc.simple.jpa.BaseService;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/7/29 16:44
 * @Description:
 */
public interface ServiceApiService1 extends BaseService<ServiceApi1DTO, ServiceApi1,String> {
    List<ServiceApi1> getAllList(String serviceId);

    String getServiceId(String applicationName);

    void updateCompareStatus(String id, String compareStatus);

//    void loadApiAuth(String serviceId,String applicationName);
}
