package com.lyc.simple.jpa;

import com.lyc.simple.entity.PrimaryKeyEntity;
import com.lyc.simple.utils.DeleteWapper;
import com.lyc.simple.utils.PredicateItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author: liuyucai
 * @Created: 2024/5/28 15:46
 * @Description:
 */
public interface BaseNativeService<ID> {

    void delete(ID id);

    void delete(DeleteWapper deleteWapper);

    void delete(List<PredicateItem> predicates);

    void delete(String table,List<PredicateItem> predicates);

    void delete(PredicateItem predicateItem);

    <V> List<V> nativeQuery(String sql, Map<String, Object> params, Class<?> entityClass, Class<V> returnClass);

    <V> Page<V> nativeQueryByPage(String sql, Pageable pageable, Map<String, Object> params, Class<?> entityClass, Class<V> returnClass);

    <V> V nativeQuerySingleResult(String sql, Map<String, Object> parameters, Class<?> entityClass, Class<V> returnClass);

    <V> Page<V> complexFindPage(Object condition, Pageable pageable, Class<? extends PrimaryKeyEntity> entityClass, Class<V> returnClass);

    <V> List<V> complexFindAll(Object condition,Class<? extends PrimaryKeyEntity> entityClass,Class<V> returnClass);

}
