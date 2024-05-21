package com.lyc.auth.service;

import com.lyc.auth.dto.OrgUserDTO;
import com.lyc.auth.dto.RoleApiDTO;
import com.lyc.auth.entity.OrgUser;
import com.lyc.simple.jpa.BaseService;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/5/1 21:41
 * @Description:
 */
public interface OrgUserService extends BaseService<OrgUserDTO, OrgUser,String> {

    String getOrgIndexByUserId(String id);

    Boolean judgeClientAuth(String orgUserId, String clientId, String inSql);

    List<String> getRoleInfo(String id, String orgUserId);

    List<RoleApiDTO> loadUserRoleApiList(List<String> roleList);
}
