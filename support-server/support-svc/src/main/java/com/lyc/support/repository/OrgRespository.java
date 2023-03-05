package com.lyc.support.repository;

import com.lyc.support.entity.Org;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author: liuyucai
 * @Created: 2023/3/5 18:30
 * @Description:
 */
public interface OrgRespository extends JpaRepository<Org, String>, JpaSpecificationExecutor<Org> {
}
