package com.lyc.support.repository;

import com.lyc.simple.jpa.BaseRepository;
import com.lyc.support.entity.DictGroup;
import com.lyc.support.entity.DictItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author: liuyucai
 * @Created: 2023/9/3 16:57
 * @Description:
 */
public interface DictItemRepository extends BaseRepository<DictItem,String> {

    @Query(value = "SELECT count(1) as dictNumber FROM sys_dict_item WHERE DELETED = 0 AND code = :code AND group_id = :groupId ", nativeQuery = true)
    Integer getCountByCode(@Param("groupId") String groupId, @Param("code") String code);

    @Query(value = "SELECT count(1) as dictNumber FROM sys_dict_item WHERE DELETED = 0 AND id != :id AND code = :code AND group_id = :groupId ", nativeQuery = true)
    Integer getCountByCode(@Param("id") String id,@Param("groupId") String groupId, @Param("code") String code);
}
