package com.lyc.support.repository;

import com.lyc.simple.jpa.BaseRepository;
import com.lyc.support.entity.OauthMenuApi;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author: liuyucai
 * @Created: 2023/11/1 8:49
 * @Description:
 */
public interface OauthMenuApiRepository extends BaseRepository<OauthMenuApi,String> {


    /**
     * 判断是否存在了
     * @param menuId
     * @param apiId
     * @return
     */
    @Query(value = "SELECT count(1) as countNumber FROM oauth_menu_api WHERE DELETED = 0 AND menu_id = :menuId AND api_id = :apiId", nativeQuery = true)
    Integer getNumberByMenuIdAndApiId(@Param("menuId") String menuId, @Param("apiId") String apiId);
}
