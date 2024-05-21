package com.lyc.support.service;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.dto.BaseDTO;
import com.lyc.simple.jpa.BaseService;
import com.lyc.support.dto.OauthServiceApiBaseDTO;
import com.lyc.support.dto.OauthServiceApiDTO;
import com.lyc.support.entity.OauthServiceApi;
import org.springframework.data.domain.Page;

/**
 * @author: liuyucai
 * @Created: 2023/7/30 12:33
 * @Description:
 */
public interface OauthServiceApiService extends BaseService<OauthServiceApiDTO, OauthServiceApi,String> {
    Page<OauthServiceApiDTO> getPageList(PageRequestVO<OauthServiceApiDTO> page);

    ResponseVO<BaseDTO> insertApi(OauthServiceApiBaseDTO oauthServiceApiBaseDTO);

    ResponseVO<BaseDTO> updateApi(OauthServiceApiBaseDTO oauthServiceApiBaseDTO);

    void deleteApi(String id);
}
