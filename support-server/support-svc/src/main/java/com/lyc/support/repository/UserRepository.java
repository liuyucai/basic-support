package com.lyc.support.repository;

import com.lyc.support.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author: liuyucai
 * @Created: 2023/2/22 8:58
 * @Description:
 */
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
}
