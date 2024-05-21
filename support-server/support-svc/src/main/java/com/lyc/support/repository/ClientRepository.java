package com.lyc.support.repository;

import com.lyc.simple.jpa.BaseRepository;
import com.lyc.support.entity.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author: liuyucai
 * @Created: 2023/3/25 21:32
 * @Description:
 */
public interface ClientRepository extends BaseRepository<Client,String> {

    /**
     * 获取是否有该clientSecret的客户端
     * @param clientSecret
     * @return
     */
    @Query(value = "SELECT count(1) as clientNumber FROM oauth_client WHERE DELETED = 0 AND client_secret = :clientSecret ", nativeQuery = true)
    Integer getClientNumberBySecret(@Param("clientSecret") String clientSecret);

    /**
     * 根据客户端密钥获取客户端信息
     * @param clientSecret
     * @return
     */
    @Query(value = "SELECT c FROM Client c WHERE DELETED = 0 AND client_secret = :clientSecret ")
    Client getByClientSecret(@Param("clientSecret") String clientSecret);


    /**
     * 根据客户端密钥获取客户端id
     * @param clientSecret
     * @return
     */
    @Query(value = "SELECT c.id FROM Client c WHERE DELETED = 0 AND client_secret = :clientSecret ")
    String getClientIdByClientSecret(@Param("clientSecret") String clientSecret);

}
