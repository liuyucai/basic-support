package com.lyc.simple.jpa;

import com.lyc.common.vo.OrderVO;
import com.lyc.simple.dto.BaseDTO;
import com.lyc.simple.entity.BaseEntity;
import com.lyc.simple.entity.PrimaryKeyEntity;
import com.lyc.simple.utils.DeleteWapper;
import com.lyc.simple.utils.PredicateItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

/**
 * @author: liuyucai
 * @Created: 2023/3/7 8:36
 * @Description:
 */
public interface BaseService<D,T extends BaseEntity<ID>,ID> {

    D save(D dto);

    void update(BaseDTO obj);

    void delete(ID id);

    void delete(DeleteWapper deleteWapper);

    void delete(List<PredicateItem> predicates);

    void delete(String table,List<PredicateItem> predicates);

    void delete(PredicateItem predicateItem);

    D get(ID id);

    <V> V get(ID var1, Class<V> returnClass);

    D getOne(D var1);

    <V> V getOne(Object var1, Class<V> returnClass);


    List<D> findAll(D condition);

    List<D> findAll(D condition, List<OrderVO> sort);

    List<D> findAll(D condition, Sort sort);

    <V> List<V> findAll(Object condition, Class<V> returnClass);

    <V> List<V> findAll(Object condition, Class<V> returnClass,Sort sort);

    <V> List<V> findAll(Object condition, Class<V> returnClass,List<OrderVO> sort);

    <V> List<V> nativeQuery(String sql, Map<String, Object> params, Class<?> entityClass, Class<V> returnClass);

    <V> Page<V> nativeQueryByPage(String sql, Pageable pageable, Map<String, Object> params, Class<?> entityClass, Class<V> returnClass);

    <V> V nativeQuerySingleResult(String sql, Map<String, Object> parameters, Class<?> entityClass, Class<V> returnClass);

    Page<D> findPage(D condition, Pageable pageable);

    <V> Page<V> findPage(D condition, Pageable pageable,Class<V> returnClass);

    <V> Page<V> complexFindPage(Object condition, Pageable pageable,Class<? extends PrimaryKeyEntity> entityClass,Class<V> returnClass);

    <V> List<V> complexFindAll(Object condition,Class<? extends PrimaryKeyEntity> entityClass,Class<V> returnClass);

}
