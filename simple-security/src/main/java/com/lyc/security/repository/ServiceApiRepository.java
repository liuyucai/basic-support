package com.lyc.security.repository;

import com.lyc.security.entity.ServiceApi1;
import com.lyc.simple.jpa.BaseRepository;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/7/29 16:43
 * @Description:
 */
public interface ServiceApiRepository extends BaseRepository<ServiceApi1,String>{

    @Query(value = "SELECT *  FROM  oauth_service_api WHERE deleted = 0 and service_id = :serviceId",nativeQuery = true)
    List<ServiceApi1> getAllList(@Param("serviceId")String serviceId);

    @Query(value = "SELECT id  FROM oauth_service WHERE deleted = 0 and code = :applicationName",nativeQuery = true)
    String getServiceId(@Param("applicationName")String applicationName);

    @Transactional
    @Modifying
    @Query(value = "update oauth_service_api  set compare_status =:compareStatus WHERE id =:id",nativeQuery = true)
    void updateCompareStatus(@Param("id")String id, @Param("compareStatus")String compareStatus);

    @Query(value = "SELECT code  FROM oauth_service WHERE id = :serviceId",nativeQuery = true)
    String getServiceCodeByServiceId(@Param("serviceId")String serviceId);
}
