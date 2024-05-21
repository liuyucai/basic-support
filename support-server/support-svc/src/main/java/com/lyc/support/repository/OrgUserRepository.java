package com.lyc.support.repository;

import com.lyc.simple.jpa.BaseRepository;
import com.lyc.support.entity.OrgUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author: liuyucai
 * @Created: 2023/5/1 21:41
 * @Description:
 */
public interface OrgUserRepository extends BaseRepository<OrgUser,String> {

    /**
     * 获取是否有该信息了
     * @param orgId、userId
     * @return
     */
    @Query(value = "SELECT count(1) as userNumber FROM sys_org_user WHERE DELETED = 0 AND org_id = :orgId AND user_id = :userId ", nativeQuery = true)
    Integer getOrgUserNumberByInfo(@Param("orgId") String orgId, @Param("userId") String userId);


    /**
     * 获取该用户的机构层级信息
     * @param id
     * @return
     */
    @Query(value = "SELECT o.org_index FROM sys_org_user u left join sys_org o on u.org_id = o.id WHERE u.id = :id ", nativeQuery = true)
    String getOrgIndexByUserId(@Param("id") String id);

    /**
     * 获取是否有主用户信息了
     * @param userId
     * @return
     */
    @Query(value = "SELECT count(1) as userNumber FROM sys_org_user WHERE DELETED = 0 AND user_id = :userId AND type ='1'", nativeQuery = true)
    Integer getMainAccountNumberByUserId(@Param("userId")String userId);

    /**
     * 获取第一个用户类型
     * @param userId
     * @return
     */
    @Query(value = "SELECT id FROM sys_org_user WHERE DELETED = 0 AND user_id = :userId order by type desc limit 0,1", nativeQuery = true)
    String getNextUserByUserId(@Param("userId") String userId);

    /**
     * 获取除id外第一个用户类型
     * @param userId
     * @return
     */
    @Query(value = "SELECT id FROM sys_org_user WHERE DELETED = 0 AND user_id = :userId AND id != :id order by type desc limit 0,1", nativeQuery = true)
    String getNextUserByUserIdAndNotId(@Param("userId") String userId,@Param("id") String id);

    /**
     * 判断用户是否有客户端权限
     * @param
     * @return
     */
    @Query(value = " SELECT COUNT(1) FROM (" +
            " SELECT r.id  FROM sys_role r LEFT JOIN sys_role_disabled d ON d.role_id = r.id AND d.org_user_id = :orgUserId AND d.deleted = 0 " +
            " WHERE r.deleted = 0  AND r.org_id IN (:orgIds) AND r.type = 'DEFAULT' AND d.id IS NULL " +
            " UNION" +
            " SELECT r.id FROM sys_role_user ru LEFT JOIN sys_role r ON ru.role_id = r.id AND r.deleted = 0 " +
            " WHERE ru.org_user_id = :orgUserId AND ru.deleted = 0 ) r " +
            " LEFT JOIN sys_role_resource1 re ON re.role_id = r.id AND re.deleted = '0' AND re.resource_id = :clientId" +
            " WHERE re.id is NOT NULL ", nativeQuery = true)
    Integer judgeUserHaveClientAuth(@Param("orgUserId") String orgUserId,@Param("orgIds") String orgIds,@Param("clientId") String clientId);
}
