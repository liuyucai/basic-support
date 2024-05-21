package com.lyc.support.repository;

import com.lyc.simple.jpa.BaseRepository;
import com.lyc.support.entity.OauthServiceAuth;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author: liuyucai
 * @Created: 2023/9/2 21:21
 * @Description:
 */
public interface OauthServiceAuthRepository extends BaseRepository<OauthServiceAuth,String> {

    @Query(value = "SELECT count(1) as authNumber FROM oauth_service_auth WHERE DELETED = 0 AND service_id = :serviceId AND auth_service_id = :authServiceId ", nativeQuery = true)
    Integer getCountAuthNumber(@Param("serviceId") String serviceId, @Param("authServiceId")String authServiceId);
}
