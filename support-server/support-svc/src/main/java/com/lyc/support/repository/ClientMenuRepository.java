package com.lyc.support.repository;

import com.lyc.simple.jpa.BaseRepository;
import com.lyc.support.entity.ClientMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/4/20 16:34
 * @Description:
 */
public interface ClientMenuRepository extends BaseRepository<ClientMenu,String> {


    /**
     * 根据id,获取其菜单及其子菜单信息
     * @param id
     * @return
     */
    @Query(value = "SELECT m  FROM ClientMenu m WHERE m.deleted = 0 and seriesIds like %:id%")
    List<ClientMenu> getMenuByLikeSeriesIds(@Param("id") String id);
}
