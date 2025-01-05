package com.lyc.simple.jpa;

import com.lyc.common.vo.OrderVO;
import com.lyc.simple.annotation.*;
import com.lyc.simple.annotation.JoinColumn;
import com.lyc.simple.annotation.JoinTable;
import com.lyc.simple.dto.BaseDTO;
import com.lyc.simple.enmus.JoinType;
import com.lyc.simple.enmus.QueryType;
import com.lyc.simple.entity.BaseEntity;
import com.lyc.simple.entity.PrimaryKeyEntity;
import com.lyc.simple.utils.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: liuyucai
 * @Created: 2023/3/7 8:36
 * @Description:
 */
@Log4j2
public abstract class BaseServiceImpl<D extends BaseDTO<ID>,T extends BaseEntity<ID>,ID> extends BaseNativeServiceImpl<ID> implements BaseService<D,T,ID>,InitializingBean, ApplicationContextAware {

    private BaseRepository<T, ID> repository;

    private Class<T> entityClazz;
    private String entityName;
    private String tableName;
    private String primaryKeyName;
    private Class<D> dtoClazz;

    /**
     * @PersistenceContext是jpa专有的注解，而@Autowired是spring自带的注释
     * EntityManager不是线程安全的，当多个请求进来的时候，spring会创建多个线程，
     * @PersistenceContext就是用来为每个线程创建一个EntityManager的，而@Autowired只创建了一个，为所有线程共用，有可能报错
     *
     */
//    @PersistenceContext
//    private EntityManager entityManager;


    public abstract BaseRepository<T,ID> getRepository();

    @Override
    public D save(D dto){

        try{
            T t = this.entityClazz.newInstance();
            //id为Null,才会返回id
            dto.setPrimaryKey(null);
            BeanUtils.copyProperties(dto,t);
            Date currentTime = new Date();
            t.setCreateTime(currentTime);
            t.setUpdateTime(currentTime);
            t.setDeleted(0);
            this.repository.saveAndFlush(t);
            dto.setPrimaryKey(t.getPrimaryKey());
            return dto;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void update(BaseDTO obj) {
        Optional<T> optional = this.repository.findById((ID) obj.getPrimaryKey());

        if (optional.isPresent()) {
            try {
                T t = optional.get();

                BeanUtils.copyProperties(obj, t);
                t.setUpdateTime(new Date());
                if (t.getDeleted() == null) {
                    t.setDeleted(0);
                }

                this.repository.saveAndFlush(t);
            } catch (BeansException var7) {
                log.error(String.valueOf(var7));
            } finally {
//                if (this.simpleJpaProperties.getThreadLogEnabled()) {
//                    ThreadLocalLogUtils.putLog(dto.getPrimaryKey(), this.dtoClazz, dto);
//                }

            }
        } else {
            log.info("update fail, not find id={} object in jpa", obj.getPrimaryKey());
        }
    }

    @Override
    public void delete(ID id){
        super.delete(id);
    };

    @Override
    public void delete(DeleteWapper deleteWapper){
        super.delete(deleteWapper);
    };

    @Override
    public void delete(List<PredicateItem> predicates){
        super.delete(predicates);
    };

    @Override
    public void delete(String table,List<PredicateItem> predicates){
        super.delete(table,predicates);
    }

    @Override
    public void delete(PredicateItem predicateItem){
        super.delete(predicateItem);
    };

    @Override
    @Transactional(readOnly = true)
    public D get(ID id){
        try {
            Optional<T> optional = this.repository.findById(id);
            if (optional.get().getDeleted() == 1) {
                log.info("id = {} is deleted", id);
                return null;
            }

            if (optional.isPresent()) {
                return this.convertEntityToDTO((T) optional.get(),dtoClazz);
            }
        } catch (NoSuchElementException var3) {
            log.error(String.valueOf(var3));
        }

        return null;
    };

    @Override
    public <V> V get(ID id, Class<V> returnClass){
        T t = (T) this.repository.getOne(id);
        if (t != null) {
            if (t.getDeleted() == 1) {
                log.info("id = {} is deleted", id);
                return null;
            } else {
                return this.convertEntityToDTO(t, returnClass);
            }
        } else {
            return null;
        }
    }

    @Override
    public D getOne(D condition) {
        Optional<T> optional = this.findOne(condition);
        return optional.isPresent() ? this.convertEntityToDTO(optional.get()) : null;
    }

    @Override
    public <V> V getOne(Object condition, Class<V> returnClass) {
        Optional<T> optional = this.findOne(condition);
        return optional.isPresent() ? this.convertEntityToDTO(optional.get(), returnClass) : null;
    }


    private Optional<T> findOne(Object condition) {
        try {
            T t = this.entityClazz.newInstance();
            BeanUtils.copyProperties(condition, t);
            t.setDeleted(0);
            Example example = Example.of(t);
            Optional<T> optional = this.repository.findOne(example);
            return optional;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<D> findAll(D condition) {
        return this.findAll(condition, (Sort)null);
    }

    @Override
    public List<D> findAll(D condition,List<OrderVO> orderVOS) {

        Sort sort = SortUtils.getSort(orderVOS);
        return this.findAll(condition, sort);
    }

    @Override
    public List<D> findAll(D condition, Sort sort) {
        List<T> all = null == sort ? this.repository.findAll(this.getSpecification(condition)) : this.repository.findAll(this.getSpecification(condition), sort);
        ArrayList content = new ArrayList(all.size());

        try {
            if (all.size() > 0) {
                Iterator iterator = all.iterator();

                while(iterator.hasNext()) {
                    T contentItem = (T)iterator.next();
                    D d = this.dtoClazz.newInstance();
                    BeanUtils.copyProperties(contentItem, d);
                    content.add(d);
                }

                return content;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    @Override
    public <V> List<V> findAll(Object condition, Class<V> returnClass){
        return this.findAll(condition,returnClass,(Sort)null);
    }

    @Override
    public <V> List<V> findAll(Object condition, Class<V> returnClass,Sort sort) {
        List<T> all = sort == null ?this.repository.findAll(this.getSpecification(condition)):this.repository.findAll(this.getSpecification(condition),sort);
        ArrayList content = new ArrayList(all.size());

        try {
            if (all.size() > 0) {
                Iterator iterator = all.iterator();

                while(iterator.hasNext()) {
                    T contentItem = (T) iterator.next();
                    V v = returnClass.newInstance();
                    BeanUtils.copyProperties(contentItem, v);
                    content.add(v);
                }

                return content;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    @Override
    public <V> List<V> findAll(Object condition, Class<V> returnClass,List<OrderVO> sort) {

        List<Sort.Order> orders=null;

        if(sort !=null){
            orders = new ArrayList<>(sort.size());

            for (OrderVO orderVO:sort) {
                if(StringUtils.isNotBlank(orderVO.getProperty())){
                    if("desc".equals(orderVO.getDirection()) || "DESC".equals(orderVO.getDirection())){
                        Sort.Order order = new Sort.Order(Sort.Direction.DESC, orderVO.getProperty());
                        orders.add(order);
                    }else if("asc".equals(orderVO.getDirection()) || "ASC".equals(orderVO.getDirection())){
                        Sort.Order order = new Sort.Order(Sort.Direction.ASC, orderVO.getProperty());
                        orders.add(order);
                    }
                }
            }
        }

        if(orders !=null){
            return this.findAll(condition,returnClass,Sort.by(orders));
        }else{
            return this.findAll(condition,returnClass,(Sort)null);
        }

    }

    @Override
    public <V> List<V> nativeQuery(String sql, Map<String, Object> params, Class<?> entityClass, Class<V> returnClass){
        return super.nativeQuery(sql,params,entityClass,returnClass);
    }

    @Override
    public <V> Page<V> nativeQueryByPage(String sql, Pageable pageable, Map<String, Object> params, Class<?> entityClass, Class<V> returnClass){
        return super.nativeQueryByPage(sql,pageable,params,entityClass,returnClass);
    }

    @Override
    public <V> V nativeQuerySingleResult(String sql, Map<String, Object> parameters, Class<?> entityClass, Class<V> returnClass){
        return super.nativeQuerySingleResult(sql,parameters,entityClass,returnClass);
    }

    @Override
    public Page<D> findPage(D condition, Pageable pageable){
        return this.findPage(condition,pageable,this.dtoClazz);
    }

    @Override
    public <V> Page<V> findPage(D condition, Pageable pageable,Class<V> returnClass){
        Specification<T> specification = getSpecification(condition);

        Page<T> page = this.repository.findAll(specification,pageable);

        ArrayList content = new ArrayList(page.getContent().size());

        try {
            if (page.getContent().size() > 0) {
                Iterator iterator = page.getContent().iterator();

                while(iterator.hasNext()) {
                    T contentItem = (T) iterator.next();
                    V v = returnClass.newInstance();
                    BeanUtils.copyProperties(contentItem, v);
                    content.add(v);
                }
                Page<V> resultPage = new PageImpl(content, pageable, page.getTotalElements());
                return resultPage;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return new PageImpl(Collections.EMPTY_LIST, pageable, 0L);
    }

    @Override
    public <V> Page<V> complexFindPage(Object condition, Pageable pageable, Class<? extends PrimaryKeyEntity> entityClass, Class<V> returnClass){
        return super.complexFindPage(condition,pageable,entityClass,returnClass);
    }

    @Override
    public <V> List<V> complexFindAll(Object condition, Class<? extends PrimaryKeyEntity> entityClass, Class<V> returnClass){
        return super.complexFindAll(condition,entityClass,returnClass);
    }

    private D convertEntityToDTO(T t) {
        try {
            if (t != null) {
                D dto = this.dtoClazz.newInstance();
                BeanUtils.copyProperties(t, dto);
                return dto;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    private <C> C convertEntityToDTO(T t, Class<C> returnClass) {
        try {
            if (t != null) {
                C c = returnClass.newInstance();
                BeanUtils.copyProperties(t, c);
                return c;
            }
        } catch (InstantiationException e) {
            log.error(e);
        } catch (IllegalAccessException e) {
            log.error(e);
        }

        return null;
    }

    // 如果某个类实现了ApplicationContextAware接口，会在类初始化完成后调用setApplicationContext()方法：
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //  SpringContextUtils.setApplicationContext(applicationContext);
    }

    // 如果某个类实现了InitializingBean接口，会在类初始化完成后，并在setApplicationContext()方法执行完毕后，调用afterPropertiesSet()方法进行操作
    @Override
    public void afterPropertiesSet() throws Exception {

        this.repository=this.getRepository();

        //获取该类的第一个泛型类型
        this.entityClazz =(Class)this.getParameterizedType().getActualTypeArguments()[1];

        super.setEntityClazz(this.entityClazz);

        //获取该类的第0个泛型类型
        this.dtoClazz = (Class)this.getParameterizedType().getActualTypeArguments()[0];

        this.entityName = this.entityClazz.getSimpleName();
        Table table = this.entityClazz.getAnnotation(Table.class);
        if(table != null){
            //entityManager.createNativeQuery 时用table名
            this.tableName = table.name();
            super.setTableName(this.tableName);
        }

        this.primaryKeyName = DbTableUtil.getPrimaryKeyName((Class)this.getParameterizedType().getActualTypeArguments()[1]);

        super.setPrimaryKeyName(this.primaryKeyName);
    }

    private ParameterizedType getParameterizedType() {
        return (ParameterizedType)this.getClass().getGenericSuperclass();
    }

    private Specification<T> getSpecification(Object condition){

        List<ConditionWapper> wappers = ConditionUtils.findObjectCondition(condition);
        Specification<T> specification = (root, query, builder) -> {
            List<Predicate> predicates = ConditionUtils.toPredicate(wappers, root, query, builder);
            predicates.add(builder.equal(root.get("deleted"), 0));
            return builder.and((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
        };
        return specification;
    }
}
