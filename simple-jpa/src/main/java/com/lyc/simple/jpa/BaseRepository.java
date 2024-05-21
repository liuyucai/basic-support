package com.lyc.simple.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author: liuyucai
 * @Created: 2023/3/7 8:52
 * @Description:  @NoRepositoryBean 使用了该注解的接口不会被单独创建实例，只会作为其他接口的父接口而被使用
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
}
