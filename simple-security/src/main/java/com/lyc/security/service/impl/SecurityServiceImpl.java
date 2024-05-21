package com.lyc.security.service.impl;

import com.lyc.security.dto.OauthClient1DTO;
import com.lyc.security.entity.OauthClient1;
import com.lyc.security.entity.ServiceApi1;
import com.lyc.security.repository.ServiceApiRepository;
import com.lyc.security.repository.ServiceClientRepository;
import com.lyc.security.service.SecurityService;
import com.lyc.security.service.ServiceApiService1;
import com.lyc.simple.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/9/1 21:41
 * @Description:
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    ServiceClientRepository serviceClientRepository;

    @Autowired
    ServiceApiRepository serviceApiRepository;

    @Autowired
    ServiceApiService1 serviceApiService1;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void loadApiAuth(String serviceId, String applicationName) {

        if(StringUtils.isBlank(applicationName)){
            applicationName = serviceApiRepository.getServiceCodeByServiceId(serviceId);
        }
        //获取已添加的api
        List<ServiceApi1> apiList1 = serviceApiService1.getAllList(serviceId);

        redisTemplate.opsForValue().set("auth:api:"+applicationName,apiList1);
    }

    @Override
    public void loadServiceClientAuth(String serviceId, String applicationName) {

        if(StringUtils.isBlank(applicationName)){
            applicationName = serviceApiRepository.getServiceCodeByServiceId(serviceId);
        }
        List<OauthClient1> list = serviceClientRepository.getClientListByServiceId(serviceId);

        List<OauthClient1DTO> list1 = new ArrayList<>(list.size());

        for (OauthClient1 oauthClient1 : list){
            OauthClient1DTO oauthClient1DTO = new OauthClient1DTO();

            BeanUtils.copyProperties(oauthClient1,oauthClient1DTO);

            list1.add(oauthClient1DTO);
        }

        redisTemplate.opsForValue().set("auth:clientService:"+applicationName,list1);
    }
}
