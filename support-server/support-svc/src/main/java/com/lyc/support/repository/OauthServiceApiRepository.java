package com.lyc.support.repository;

import com.lyc.simple.jpa.BaseRepository;
import com.lyc.support.entity.OauthServiceApi;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author: liuyucai
 * @Created: 2023/7/30 12:33
 * @Description:
 */
public interface OauthServiceApiRepository extends BaseRepository<OauthServiceApi,String> {

    @Query(value = "SELECT count(1) as apiNumber FROM oauth_service_api WHERE DELETED = 0 AND service_id = :serviceId AND format_url = :formatUrl ", nativeQuery = true)
    Integer getCountUrlNumber(@Param("serviceId") String serviceId, @Param("formatUrl")String formatUrl);
}
