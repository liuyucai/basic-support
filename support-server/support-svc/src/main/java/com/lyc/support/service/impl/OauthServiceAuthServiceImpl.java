package com.lyc.support.service.impl;

import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.support.dto.OauthServiceAuthDTO;
import com.lyc.support.entity.OauthServiceAuth;
import com.lyc.support.repository.OauthServiceAuthRepository;
import com.lyc.support.service.OauthServiceAuthService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: liuyucai
 * @Created: 2023/9/2 21:22
 * @Description:
 */
@Service
@Log4j2
public class OauthServiceAuthServiceImpl extends SimpleServiceImpl<OauthServiceAuthDTO, OauthServiceAuth,String, OauthServiceAuthRepository> implements OauthServiceAuthService {

    @Autowired
    OauthServiceAuthRepository oauthServiceAuthRepository;

    @Override
    public ResponseVO<OauthServiceAuthDTO> saveServiceAuth(OauthServiceAuthDTO oauthServiceAuthDTO) {

        //先获取是否有该资源授权了
        Integer number = oauthServiceAuthRepository.getCountAuthNumber(oauthServiceAuthDTO.getServiceId(),oauthServiceAuthDTO.getAuthServiceId());

        //如果没有再添加

        if(number == null || number.intValue() == 0){
            oauthServiceAuthDTO.setId(null);
            this.save(oauthServiceAuthDTO);
            return ResponseVO.success(oauthServiceAuthDTO);
        }else{
            return ResponseVO.fail();
        }
    }
}
