package com.lyc.support.repository;

import com.lyc.simple.jpa.BaseRepository;
import com.lyc.support.entity.DictGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author: liuyucai
 * @Created: 2023/9/3 16:56
 * @Description:
 */
public interface DictGroupRepository extends BaseRepository<DictGroup,String> {

    @Query(value = "SELECT count(1) as dictNumber FROM sys_dict_group WHERE DELETED = 0 AND code = :code AND service_id = :serviceId ", nativeQuery = true)
    Integer getCountByCodeAndServiceId(@Param("code") String code, @Param("serviceId") String serviceId);

    @Query(value = "SELECT count(1) as dictNumber FROM sys_dict_group WHERE DELETED = 0 AND id != :id AND code = :code AND service_id = :serviceId ", nativeQuery = true)
    Integer getCountByCodeAndServiceId(@Param("id") String id, @Param("code") String code, @Param("serviceId") String serviceId);

    @Query(value = "SELECT count(1) as dictNumber FROM sys_dict_group WHERE DELETED = 0 AND code = :code AND service_id is null ", nativeQuery = true)
    Integer getCountByCode(@Param("code") String code);

    @Query(value = "SELECT count(1) as dictNumber FROM sys_dict_group WHERE DELETED = 0 AND id != :id AND code = :code AND service_id is null ", nativeQuery = true)
    Integer getCountByCode(@Param("id") String id, @Param("code") String code);

}
