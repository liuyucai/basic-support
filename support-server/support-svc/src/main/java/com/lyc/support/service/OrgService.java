package com.lyc.support.service;

import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.BaseService;
import com.lyc.support.dto.OrgDTO;
import com.lyc.support.dto.OrgReqDTO;
import com.lyc.support.entity.Org;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/3/5 18:31
 * @Description:
 */
public interface OrgService extends BaseService<OrgDTO,Org,String> {
    ResponseVO<OrgDTO> insertOrg(OrgDTO orgDTO);

    ResponseVO<OrgDTO> updateOrg(OrgDTO orgDTO);

    List<OrgDTO> getAllList(OrgReqDTO orgReqDTO);

    List<OrgDTO> getOrgListByUserId(String userId);
}
