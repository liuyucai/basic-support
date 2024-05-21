package com.lyc.support.repository;

import com.lyc.simple.jpa.BaseRepository;
import com.lyc.support.entity.RoleUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/5/20 16:46
 * @Description:
 */
public interface RoleUserRepository extends BaseRepository<RoleUser,String> {


    /**
     * 获取不存在该roleIds的记录
     * @param orgUserId、roleIds
     * @return
     */
    @Query(value = "SELECT id FROM sys_role_user WHERE DELETED = 0 AND org_user_id = :orgUserId AND roleId  not in (:roleIds) ", nativeQuery = true)
    List<String> getNotInRoleIds(@Param("orgUserId") String orgUserId, @Param("roleIds") String roleIds);


    /**
     * 获取是否有该信息了
     * @param orgUserId、roleId
     * @return
     */
    @Query(value = "SELECT count(1) as userNumber FROM sys_role_user WHERE DELETED = 0 AND org_user_id = :orgUserId AND role_id = :roleId ", nativeQuery = true)
    Integer getNumberByInfo(@Param("orgUserId") String orgUserId, @Param("roleId") String roleId);


}
