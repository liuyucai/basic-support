package com.lyc.support.repository;

import com.lyc.simple.jpa.BaseRepository;
import com.lyc.support.entity.Org;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/3/5 18:30
 * @Description:
 */
public interface OrgRepository extends BaseRepository<Org,String> {

    /**
     * 获取不存在该roleIds的记录
     * @param orgIds
     * @return
     */
    @Query(value = "SELECT * FROM sys_org WHERE DELETED = 0 AND id  not in (:orgIds) ", nativeQuery = true)
    List<Org> getListByOrgList(@Param("orgIds")String orgIds);
}
