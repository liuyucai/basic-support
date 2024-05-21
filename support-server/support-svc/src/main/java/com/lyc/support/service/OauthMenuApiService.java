package com.lyc.support.service;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.BaseService;
import com.lyc.support.dto.OauthMenuApiDTO;
import com.lyc.support.dto.OauthMenuApiReqDTO;
import com.lyc.support.dto.OauthMenuApiRespDTO;
import com.lyc.support.entity.OauthMenuApi;
import org.springframework.data.domain.Page;

/**
 * @author: liuyucai
 * @Created: 2023/11/1 8:50
 * @Description:
 */
public interface OauthMenuApiService extends BaseService<OauthMenuApiDTO, OauthMenuApi,String> {
    Page<OauthMenuApiRespDTO> getPageList(PageRequestVO<OauthMenuApiReqDTO> page);

    ResponseVO openApiAuth(OauthMenuApiDTO oauthMenuApiDTO);

    ResponseVO closeApiAuth(String id);

    Page<OauthMenuApiRespDTO> getAllPageList(PageRequestVO<OauthMenuApiReqDTO> page);
}
