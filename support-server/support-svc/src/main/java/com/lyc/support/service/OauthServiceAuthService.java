package com.lyc.support.service;

import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.BaseService;
import com.lyc.support.dto.OauthServiceAuthDTO;
import com.lyc.support.entity.OauthServiceAuth;

/**
 * @author: liuyucai
 * @Created: 2023/9/2 21:22
 * @Description:
 */
public interface OauthServiceAuthService extends BaseService<OauthServiceAuthDTO, OauthServiceAuth,String> {
    ResponseVO<OauthServiceAuthDTO> saveServiceAuth(OauthServiceAuthDTO oauthServiceAuthDTO);
}
