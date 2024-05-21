package com.lyc.support.service;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.PageResponseVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.BaseService;
import com.lyc.support.dto.*;
import com.lyc.support.entity.OrgUser;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/5/1 21:41
 * @Description:
 */
public interface OrgUserService extends BaseService<OrgUserDTO, OrgUser,String> {
    List<OrgDTO> getOrgListByUserId(String userId);

    ResponseVO<OrgUserSaveDTO> saveOrgUser(OrgUserSaveDTO orgUserSaveDTO);

    ResponseVO<OrgUserSaveDTO> updateOrgUser(OrgUserSaveDTO orgUserSaveDTO);

    Page<OrgUserPageRespDTO> getPageList(PageRequestVO<OrgUserPageReqDTO> page);

    ResponseVO<OrgUserDetailDTO> getDetailById(String id);


    Page<OrgUserRolePageRespDTO> getRoleSetting(PageRequestVO<OrgUserRolePageReqDTO> page);

    ResponseVO<LoginUserDetailDTO> getLoginUserDetail();

    ResponseVO deleteUser(String id);

    ResponseVO updateUserType(UpdateOrgUserTypeDTO updateOrgUserTypeDTO);

    ResponseVO<List<OrgUserDTO>> getAllList(OrgUserDTO orgUserDTO);

    ResponseVO<List<OrgUserDTO>> getClientAuthUserList(ClientAuthOrgUserReqDTO clientAuthOrgUserReqDTO);
}
