package com.lyc.auth.repository;

import com.lyc.auth.entity.OrgUser;
import com.lyc.simple.jpa.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/5/1 21:41
 * @Description:
 */
public interface OrgUserRepository extends BaseRepository<OrgUser,String> {

    /**
     * 获取该用户的机构层级信息
     * @param id
     * @return
     */
    @Query(value = "SELECT o.org_index FROM sys_org_user u left join sys_org o on u.org_id = o.id WHERE u.id = :id ", nativeQuery = true)
    String getOrgIndexByUserId(@Param("id") String id);

    /**
     * 获取该用户的角色信息
     * @param orgUserId
     * @return
     */
    @Query(value = "select r.id from sys_role r left join sys_role_disabled d on d.org_user_id = :orgUserId  and d.deleted = 0 where r.deleted = 0 and org_id in (:orgIds) and type = 'DEFAULT' " +
            "       UNION  " +
            "       select r.id from sys_role_user ru left join sys_role r on ru.role_id = r.id  and r.deleted = 0 where ru.org_user_id = :orgUserId  and ru.deleted = 0", nativeQuery = true)
    List<String> getRoleInfo(@Param("orgUserId")String orgUserId, @Param("orgIds")String orgIds);
}
