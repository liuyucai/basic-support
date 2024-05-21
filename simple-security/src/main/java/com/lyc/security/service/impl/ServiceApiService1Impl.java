package com.lyc.security.service.impl;

import com.lyc.security.dto.ServiceApi1DTO;
import com.lyc.security.entity.OauthClient1;
import com.lyc.security.entity.ServiceApi1;
import com.lyc.security.repository.ServiceApiRepository;
import com.lyc.security.service.ServiceApiService1;
import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.simple.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/7/29 16:44
 * @Description:
 */
@Service
public class ServiceApiService1Impl extends SimpleServiceImpl<ServiceApi1DTO, ServiceApi1,String, ServiceApiRepository> implements ServiceApiService1 {

    @Autowired
    ServiceApiRepository serviceApiRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<ServiceApi1> getAllList(String serviceId) {

        List<ServiceApi1> list = serviceApiRepository.getAllList(serviceId);

        return list;
    }

    @Override
    public String getServiceId(String applicationName) {

        String id = serviceApiRepository.getServiceId(applicationName);
        return id;
    }

    @Override
    public void updateCompareStatus(String id,String compareStatus) {
        serviceApiRepository.updateCompareStatus(id,compareStatus);
    }

//    @Override
//    public void loadApiAuth(String serviceId, String applicationName) {
//
//        if(StringUtils.isBlank(applicationName)){
//            applicationName = serviceApiRepository.getServiceCodeByServiceId(serviceId);
//        }
//        //获取已添加的api
//        List<ServiceApi1> apiList1 = this.getAllList(serviceId);
//
//        //
//        redisTemplate.opsForValue().set("auth:api:"+applicationName,apiList1);
//    }

}
