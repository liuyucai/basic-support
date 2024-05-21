package com.lyc.support.repository;

import com.lyc.simple.jpa.BaseRepository;
import com.lyc.support.entity.ClientMenu;
import com.lyc.support.entity.ClientRouter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/4/23 18:25
 * @Description:
 */
public interface ClientRouterRepository extends BaseRepository<ClientRouter,String> {

    /**
     * 根据id,获取其菜单及其子菜单信息
     * @param id
     * @return
     */
    @Query(value = "SELECT id  FROM ClientRouter  WHERE deleted = 0 and pid = :id")
    List<String> getFunctionIdListByRouterId(@Param("id") String id);
}
