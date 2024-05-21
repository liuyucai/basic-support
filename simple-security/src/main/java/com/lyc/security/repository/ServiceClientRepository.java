package com.lyc.security.repository;

import com.lyc.security.entity.OauthClient1;
import com.lyc.security.entity.ServiceApi1;
import com.lyc.simple.jpa.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/9/1 21:39
 * @Description:
 */
public interface ServiceClientRepository extends BaseRepository<OauthClient1,String> {

    @Query(value = "SELECT c.*  FROM  oauth_client_service  s left join oauth_client c on c.id = s.client_id and s.deleted = 0 and c.deleted = 0 WHERE s.service_id = :serviceId and c.id is not null",nativeQuery = true)
    List<OauthClient1> getClientListByServiceId(String serviceId);
}
