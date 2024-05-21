package com.lyc.support.repository;

import com.lyc.simple.jpa.BaseRepository;
import com.lyc.support.entity.OauthClientService;
import com.lyc.support.entity.OauthService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author: liuyucai
 * @Created: 2023/4/17 8:49
 * @Description:
 */
public interface ClientServiceRepository extends BaseRepository<OauthClientService,String> {

    /**
     * 获取是否有该信息了
     * @param clientId、serviceId
     * @return
     */
    @Query(value = "SELECT count(1) as clientServiceNumber FROM oauth_client_service WHERE DELETED = 0 AND client_id = :clientId AND service_id = :serviceId ", nativeQuery = true)
    Integer getClientServiceNumberByInfo(@Param("clientId") String clientId,@Param("serviceId") String serviceId);
}
