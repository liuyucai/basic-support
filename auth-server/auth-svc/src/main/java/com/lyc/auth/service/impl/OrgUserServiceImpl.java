package com.lyc.auth.service.impl;

import com.lyc.auth.dto.OrgUserDTO;
import com.lyc.auth.dto.RoleApiDTO;
import com.lyc.auth.dto.RoleClientDTO;
import com.lyc.auth.entity.OrgUser;
import com.lyc.auth.entity.QO.RoleApiQO;
import com.lyc.auth.entity.QO.RoleClientQO;
import com.lyc.auth.repository.OrgUserRepository;
import com.lyc.auth.service.OrgUserService;
import com.lyc.auth.sql.OrgUserSqlConfig;
import com.lyc.simple.jpa.SimpleServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: liuyucai
 * @Created: 2023/5/1 21:41
 * @Description:
 */
@Service
@Log4j2
public class OrgUserServiceImpl extends SimpleServiceImpl<OrgUserDTO, OrgUser,String, OrgUserRepository> implements  OrgUserService{


    @Autowired
    OrgUserSqlConfig orgUserSqlConfig;

    @Autowired
    OrgUserRepository orgUserRepository;

    @Override
    public String getOrgIndexByUserId(String id) {
        return orgUserRepository.getOrgIndexByUserId(id);
    }

    @Override
    public Boolean judgeClientAuth(String orgUserId, String clientId, String inSql) {

        StringBuilder sql = new StringBuilder(orgUserSqlConfig.getQueryRoleClientByIdSql().getColumns());
        sql.append(orgUserSqlConfig.getQueryRoleClientByIdSql().getTables());

        Map<String, Object> params = new HashMap<>(1);
        params.put("orgUserId",orgUserId);
        params.put("orgIds",inSql);
        params.put("clientId",clientId);

        List<RoleClientDTO> list = this.nativeQuery(sql.toString(),params, RoleClientQO.class, RoleClientDTO.class);

        if(list.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public List<String> getRoleInfo(String id, String orgUserId) {
        return orgUserRepository.getRoleInfo(id,orgUserId);
    }

    @Override
    public List<RoleApiDTO> loadUserRoleApiList(List<String> roleList) {

        String inSql= "";
        int index =0;
        for(String roleId:roleList){
            if(index == 0){
                inSql = "\'"+roleId+"\'";
            }else{
                inSql = inSql+",\'"+roleId+"\'";
            }
            index++;
        }

        String sql = " select s.name as service_name,sa.id,sa.url from sys_role_resource1 r " +
                " left join oauth_menu_api a on a.menu_id = r.id and a.deleted = '0' " +
                " left join oauth_service_api sa on a.api_id = sa.id and sa.deleted = '0'  " +
                " left join oauth_service s on sa.service_id = s.id  " +
                " where r.role_id in ("+inSql+")  and r.resource_type in ('MENU','ROUTER','FUNCTION') AND sa.id is not null   GROUP BY sa.id ";

        List<RoleApiDTO> list = this.nativeQuery(sql,null, RoleApiQO.class, RoleApiDTO.class);

        return  list;
    }
}
