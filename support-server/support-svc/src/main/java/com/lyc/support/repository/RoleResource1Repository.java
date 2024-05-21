package com.lyc.support.repository;

import com.lyc.simple.jpa.BaseRepository;
import com.lyc.support.entity.RoleResource1;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author: liuyucai
 * @Created: 2023/9/21 8:51
 * @Description:
 */
public interface RoleResource1Repository extends BaseRepository<RoleResource1,String> {

    /**
     * 判断是否有该资源的权限
     * @param resourceId
     * @return
     */
    @Query(value = "SELECT count(1) as countNumber FROM sys_role_resource WHERE DELETED = 0 AND role_id = :roleId AND resource_id = :resourceId", nativeQuery = true)
    Integer getNumberByResourceIdAndRoleId(@Param("roleId") String roleId, @Param("resourceId") String resourceId);


}
