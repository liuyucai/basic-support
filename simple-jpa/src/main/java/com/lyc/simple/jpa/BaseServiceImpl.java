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
public abstract class BaseServiceImpl<D extends BaseDTO<ID>,T extends BaseEntity<ID>,ID> implements BaseService<D,T,ID>, InitializingBean, ApplicationContextAware {

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
    @PersistenceContext
    private EntityManager entityManager;


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
    @Transactional(rollbackFor = Exception.class)
    public void delete(ID id){

        String sql = "update " + this.tableName + " set deleted =1 where " + this.primaryKeyName + " = :id";
        log.info("delete sql = {}", sql);
        Query query = this.entityManager.createNativeQuery(sql);
        query.setParameter("id", id);
        query.executeUpdate();
//        if (this.simpleJpaProperties.getThreadLogEnabled()) {
//            ThreadLocalLogUtils.putLog(id, this.dtoClazz, (Object)null);
//        }
    };

    @Override
    public void delete(DeleteWapper deleteWapper){

        delete(deleteWapper.getTable(),deleteWapper.getPredicates());
    };

    @Override
    public void delete(List<PredicateItem> predicates){
        delete(null,predicates);
    };

    @Override
    @Transactional(readOnly = false)
    public void delete(String table,List<PredicateItem> predicates){

        if(StringUtils.isBlank(table)){
            table = this.tableName;
        }

        StringBuilder stringBuilder = new StringBuilder("update " + table + " set deleted =1 where deleted = 0 ");


        Map<String,Object> param = new HashMap<>(predicates.size());

        int index = 0;
        for (PredicateItem predicateItem:predicates) {
            if(StringUtils.isNotBlank(predicateItem.getName()) && StringUtils.isNotBlank(predicateItem.getColumn())){
                if(predicateItem.getType() == QueryType.EQUAL){
                    stringBuilder.append(" AND "+ predicateItem.getColumn() + " =:"+predicateItem.getName());
                    param.put(predicateItem.getName(),predicateItem.getValue());
                }else if(predicateItem.getType() == QueryType.LIKE){
                    stringBuilder.append(" AND "+ predicateItem.getColumn() + " like %:"+predicateItem.getName() +"%");
                    param.put(predicateItem.getName(),predicateItem.getValue());
                }else if(predicateItem.getType() == QueryType.IN){
                    stringBuilder.append(" AND "+ predicateItem.getColumn() + " in ("+predicateItem.getValue() +")");
//                    stringBuilder.append(" AND "+ predicateItem.getColumn() + " in ( :"+predicateItem.getName() +")");
//                    param.put(predicateItem.getName(),predicateItem.getValue());
                }else if(predicateItem.getType() == QueryType.BETWEEN){

                    String startAttributeName = "startAttribute"+index;
                    String endAttributeName = "endAttribute"+index;
                    stringBuilder.append(" AND "+ predicateItem.getColumn() + " between :" +startAttributeName + " and :"+startAttributeName);

                    param.put(startAttributeName,predicateItem.getStartValue());
                    param.put(endAttributeName,predicateItem.getEndValue());
                    index++;
                }else if(predicateItem.getType() == QueryType.NOT_IN){
                    stringBuilder.append(" AND "+ predicateItem.getColumn() + " not in(:"+predicateItem.getName()+")");
                    param.put(predicateItem.getName(),predicateItem.getValue());
                }
            }
        }

        String sql = stringBuilder.toString();
        log.info("delete sql = {}", sql);
        Query query = this.entityManager.createNativeQuery(sql);

        for (Map.Entry<String, Object> entry : param.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        query.executeUpdate();
//        if (this.simpleJpaProperties.getThreadLogEnabled()) {
//            ThreadLocalLogUtils.putLog(id, this.dtoClazz, (Object)null);
//        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(PredicateItem predicateItem){

        StringBuilder stringBuilder = new StringBuilder("update " + this.tableName + " set deleted =1 where ");

        String sql ="";
        Query query = null;

        if(StringUtils.isNotBlank(predicateItem.getName()) && StringUtils.isNotBlank(predicateItem.getColumn())){
            if(predicateItem.getType() == QueryType.EQUAL){
                stringBuilder.append(" "+ predicateItem.getColumn() + "=:"+predicateItem.getName());
                sql = stringBuilder.toString();
                query = this.entityManager.createNativeQuery(sql);
                query.setParameter(predicateItem.getName(), predicateItem.getValue());
            }else if(predicateItem.getType() == QueryType.LIKE){
                stringBuilder.append(" "+ predicateItem.getColumn() + "like %:"+predicateItem.getName() +"%");
                query = this.entityManager.createNativeQuery(sql);
                query.setParameter(predicateItem.getName(), predicateItem.getValue());
            }else if(predicateItem.getType() == QueryType.IN){
                stringBuilder.append(" "+ predicateItem.getColumn() + "in (:"+predicateItem.getName() +")");
                query = this.entityManager.createNativeQuery(sql);
                query.setParameter(predicateItem.getName(), predicateItem.getValue());
            }else if(predicateItem.getType() == QueryType.BETWEEN){
                stringBuilder.append(" "+ predicateItem.getColumn() + "between :startAttribute and :startAttributeName");

                query = this.entityManager.createNativeQuery(sql);
                query.setParameter("startAttribute", predicateItem.getStartValue());
                query.setParameter("endAttribute", predicateItem.getEndValue());
            }
        }

        log.info("delete sql = {}", sql);
        query.executeUpdate();
//        if (this.simpleJpaProperties.getThreadLogEnabled()) {
//            ThreadLocalLogUtils.putLog(id, this.dtoClazz, (Object)null);
//        }
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
        log.info("nativeQuery = [{}]", sql);
        Query query = this.entityManager.createNativeQuery(sql, entityClass);
        List<?> list = this.initQueryParams(query, params).getResultList();
        ArrayList content = new ArrayList();

        try {
            Iterator iterator = list.iterator();

            while(iterator.hasNext()) {
                Object contentItem = iterator.next();
                V v = returnClass.newInstance();
                BeanUtils.copyProperties(contentItem, v);
                content.add(v);
            }
        } catch (InstantiationException var11) {
            var11.printStackTrace();
        } catch (IllegalAccessException var12) {
            var12.printStackTrace();
        }
        return content;
    }

    @Override
    public <V> Page<V> nativeQueryByPage(String sql, Pageable pageable, Map<String, Object> params, Class<?> entityClass, Class<V> returnClass){

        sql.replaceAll("FROM","from");

        //获取第一个from 的位置
        int startIndex = sql.indexOf("from");

        StringBuilder countQuerySql = (new StringBuilder("select count(1) from ")).append(sql.substring(startIndex+4,sql.length()));
        Query countQuery = this.entityManager.createNativeQuery(countQuerySql.toString());
        BigInteger count = (BigInteger)this.initQueryParams(countQuery, params).getSingleResult();

        StringBuilder querySql = new StringBuilder(sql);

        int pageSize = pageable.getPageSize();
        long offset = pageable.getOffset();
        if (pageable.getSort().isSorted()) {
            querySql.append(" order by " + pageable.getSort().toString().replaceAll(":", ""));
        }

        querySql.append(SQLTranslator.paging());
        params.put("offset", offset);
        params.put("size", pageSize);
        List<V> content = this.nativeQuery(querySql.toString(), params, entityClass, returnClass);
        return new PageImpl(content, pageable, (long)count.intValue());
    }

    @Override
    public <V> V nativeQuerySingleResult(String sql, Map<String, Object> parameters, Class<?> entityClass, Class<V> returnClass){
        Query query = this.entityManager.createNativeQuery(sql, entityClass);

        try {
            Object result = this.initQueryParams(query, parameters).getSingleResult();
            V v = returnClass.newInstance();
            BeanUtils.copyProperties(result, v);
            return v;
        } catch (InstantiationException var8) {
            var8.printStackTrace();
        } catch (IllegalAccessException var9) {
            var9.printStackTrace();
        } catch (NoResultException var10) {
            log.info("查找的记录不存在");
        }
        return null;
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

        ComplexSqlWapper complexSqlWapper = this.translateSql(condition,entityClass);
        return this.nativeQueryByPage(complexSqlWapper.getSql(),pageable,complexSqlWapper.getParams(),entityClass,returnClass);
    }

    @Override
    public <V> List<V> complexFindAll(Object condition, Class<? extends PrimaryKeyEntity> entityClass, Class<V> returnClass){

        ComplexSqlWapper complexSqlWapper = this.translateSql(condition,entityClass);
        return this.nativeQuery(complexSqlWapper.getSql(),complexSqlWapper.getParams(),entityClass,returnClass);
    }

    private Query initQueryParams(Query query,Map<String, Object> params){
        if (params == null) {
            params = new HashMap<String, Object>(1);
        }

        if (params.size() > 0) {
            Iterator iterator = params.keySet().iterator();

            while(iterator.hasNext()) {
                String key = (String)iterator.next();
                query.setParameter(key, params.get(key));
            }
        }
        return query;
    };

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

        //获取该类的第0个泛型类型
        this.dtoClazz = (Class)this.getParameterizedType().getActualTypeArguments()[0];

        this.entityName = this.entityClazz.getSimpleName();
        Table table = this.entityClazz.getAnnotation(Table.class);
        if(table != null){
            //entityManager.createNativeQuery 时用table名
            this.tableName = table.name();
        }

        this.primaryKeyName = DbTableUtil.getPrimaryKeyName((Class)this.getParameterizedType().getActualTypeArguments()[1]);

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


    private ComplexSqlWapper translateSql(Object condition,Class<? extends PrimaryKeyEntity> entityClass){

        StringBuilder sql = new StringBuilder("select ");


        JoinTable[] joinTableArr =  null;

        //获取condition 的关联表
        JoinTable joinTable = entityClass.getAnnotation(JoinTable.class);

        JoinTables leftJoinTables = entityClass.getAnnotation(JoinTables.class);

        JoinTable[]  joinTables = leftJoinTables == null?null:leftJoinTables.value();

        int leftJoinTableNum = 0;

        if(joinTable != null){
            leftJoinTableNum++;
        }

        if(joinTables != null){
            leftJoinTableNum = leftJoinTableNum+joinTables.length;
        }
        joinTableArr = new JoinTable[leftJoinTableNum];

        int firstIndex = 0;
        if(joinTable != null){
            joinTableArr[0] = joinTable;
            firstIndex = 1;
        }
        if(joinTables != null){
            leftJoinTableNum = leftJoinTableNum+joinTables.length;
            for(int i = 0;i<joinTables.length;i++){
                joinTableArr[i+firstIndex] = joinTables[i];
            }
        }


        //tableName -> 别名
        Map<String,String> tablesName= new HashMap<>(leftJoinTableNum+1);

        //别名 -> tableName
        Map<String,String> aliasTablesName= new HashMap<>(leftJoinTableNum+1);

        //tableName -> sql
        Map<String,StringBuilder> leftJoinSqls = new HashMap<>(leftJoinTableNum+1);

        Map<String,Object> params = new LinkedHashMap<>();

        String aliasTableName = "_"+UUID.randomUUID().toString().replaceAll("-","").trim();

        for (JoinTable joinTable1 : joinTableArr) {

            String name = joinTable1.name();
            if(StringUtils.isBlank(name)){
                name = "_"+UUID.randomUUID().toString().replaceAll("-","").trim();
            }

            StringBuilder sql1 = new StringBuilder();

            if(StringUtils.isBlank(joinTable1.nativeSql()) && StringUtils.isNotBlank(joinTable1.table())){
                tablesName.put(joinTable1.table(),name);
                aliasTablesName.put(name, joinTable1.table());

                if(joinTable1.joinType() == JoinType.LEFT){
                    sql1.append(" left join "+ joinTable1.table() +" " + joinTable1.name());
                }else if(joinTable1.joinType() == JoinType.RIGHT){
                    sql1.append(" right join "+ joinTable1.table() +" " + joinTable1.name());
                }else{
                    sql1.append(" join "+ joinTable1.table() +" " + joinTable1.name());
                }
            }else if(StringUtils.isNotBlank(joinTable1.nativeSql())){
                tablesName.put(name,name);
                aliasTablesName.put(name,name);

                if(joinTable1.joinType() == JoinType.LEFT){
                    sql1.append(" left join ( "+ joinTable1.nativeSql() +" ) " + joinTable1.name());
                }else if(joinTable1.joinType() == JoinType.RIGHT){
                    sql1.append(" right join ( "+ joinTable1.nativeSql() +" ) " + joinTable1.name());
                }else{
                    sql1.append(" join ( "+ joinTable1.nativeSql() +" ) " + joinTable1.name());
                }

            }


            boolean hasCondition = false;

            if(StringUtils.isNotBlank(joinTable1.columnName()) && StringUtils.isNotBlank(joinTable1.referencedColumnName())){
                sql1.append(" on "+aliasTableName+"."+ joinTable1.columnName() + " = "+name+"."+ joinTable1.referencedColumnName());

                if(joinTable1.autoFilterDeleted()){
                    sql1.append(" and "+name+"."+"deleted = '0'");
                }

                hasCondition = true;
            }

            OnCondition [] conditions = joinTable1.conditions();
            if(conditions != null){
                for (OnCondition onCondition:conditions){
                    if(StringUtils.isNotBlank(onCondition.columnName()) && StringUtils.isNotBlank(onCondition.referencedColumnName())){
                        if(hasCondition){
                            sql1.append(" AND ");
                        }
                        sql1.append(" on "+aliasTableName+"."+onCondition.columnName() + " = "+name+"."+onCondition.referencedColumnName());
                        hasCondition = true;
                    }
                }
            }
            leftJoinSqls.put(joinTable1.table(),sql1);
        }


        AtomicInteger columnIndex = new AtomicInteger();

        //初始化left join 的sql

        //遍历entity,获取查询字段
        Field[] entityFields = entityClass.getDeclaredFields();
        Arrays.asList(entityFields).forEach((field) -> {
            if (!field.getName().equals("serialVersionUID")) {
                //只有带有Column注解的才是查询字段
                if(field.isAnnotationPresent(Column.class)){
                    field.setAccessible(true);
                    Column column = field.getAnnotation(Column.class);
                    //如果有JoinColumn字段,
                    if(field.isAnnotationPresent(JoinColumn.class)){
                        JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
                        //如果有tableName字段，要判断是别名还是表名
                        if(StringUtils.isNotBlank(joinColumn.tableName())){
                            //先获取别名
                            String aliasName = joinColumn.tableName();
                            if(StringUtils.isBlank(aliasTablesName.get(joinColumn.tableName()))){
                                //如果是表名
                                if(StringUtils.isNotBlank(aliasTablesName.get(joinColumn.tableName()))){
                                    aliasName=tablesName.get(joinColumn.tableName());
                                }
                            }
                            if(columnIndex.get() == 0){
                                sql.append(aliasName.trim()+"."+joinColumn.name().trim()+" AS "+column.name());
                            }else{
                                sql.append(" ,"+aliasName.trim()+"."+joinColumn.name().trim()+" AS "+column.name());
                            }
                            columnIndex.getAndIncrement();
                        }else {
                            if(columnIndex.get() == 0){
                                sql.append(" "+aliasTableName+"."+column.name());
                            }else{
                                sql.append(" ,"+aliasTableName+"."+column.name());
                            }
                            columnIndex.getAndIncrement();
                        }
                    }else{
                        if(columnIndex.get() == 0){
                            sql.append(" "+aliasTableName+"."+column.name());
                        }else{
                            sql.append(" ,"+aliasTableName+"."+column.name());
                        }
                        columnIndex.getAndIncrement();
                    }
                }
            }
        });

        StringBuilder  whereSql = new StringBuilder(" where 1=1 ");



        //遍历dto字段，获取查询条件
        Field[] fields = condition.getClass().getDeclaredFields();

        Arrays.asList(fields).forEach((field) -> {
            if (!field.getName().equals("serialVersionUID")) {
                if (!field.isAnnotationPresent(ConditionIgnore.class)) {
                    try {
                        field.setAccessible(true);

                        boolean flag = true;

                        Object value = field.get(condition);

//                        Annotation annotation = ConditionType.getAnnotation(field);
//                        Class<?> annotationType = Equal.class;
//                        if (annotation != null) {
//                            annotationType = annotation.annotationType();
//                        }

                        Object val = field.get(condition);

                        //先初始化sql
                        if(field.isAnnotationPresent(On.class)){
                            On on = field.getAnnotation(On.class);

                            String tableName = on.table();

                            if(StringUtils.isNotBlank(tableName)){

                                boolean hasTable = false;
                                if(StringUtils.isNotBlank(tablesName.get(tableName))){
                                    //如果用的是table名
                                    hasTable = true;
                                }else if(StringUtils.isNotBlank(aliasTablesName.get(tableName))){
                                    //如果用的是别名
                                    tableName = aliasTablesName.get(tableName);
                                    hasTable = true;
                                }
                                if(hasTable){
                                    StringBuilder leftJoinSql = leftJoinSqls.get(tableName);
                                    if(StringUtils.isNotBlank(on.sql())){
                                        leftJoinSql.append(" AND "+ on.sql());
                                    }
                                    //判断是null,还是"",
                                    if(StringUtils.isNotBlank(on.condition())){
                                        if(val == null) {
                                            String aliasName = tablesName.get(tableName);
                                            leftJoinSql.append(" AND "+ aliasName.trim()+"."+on.condition() + " is null");
                                        }else{
                                            //判断是否String类型
                                            String val1 = (String) field.get(condition);
                                            if("java.lang.String".equals(field.getType().getTypeName())){
                                                if(StringUtils.isBlank(val1)){
                                                    //如果是String类型，且允许为null
                                                    if(field.isAnnotationPresent(Empty.class)){
                                                        String aliasName = tablesName.get(tableName);
                                                        leftJoinSql.append(" AND "+ aliasName.trim()+"."+on.condition() + " is null ");
                                                    }
                                                }else{
                                                    String aliasName = tablesName.get(tableName);
                                                    leftJoinSql.append(" AND "+ aliasName.trim()+"."+on.condition() + " = "+val1);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }else if(field.isAnnotationPresent(Equal.class)){
                            //equal,  要判断是否为null 或String
                            if(val == null) {
                                if (field.isAnnotationPresent(nullable.class)) {

                                    try{
                                        //1、获取指定的属性
                                        Field fileName = entityClazz.getDeclaredField(field.getName());
                                        //2、通过getAnnotation方法获取该属性上指定的注解，注意给的参数
                                        Column column = fileName.getAnnotation(Column.class);
                                        whereSql.append(" AND "+aliasTableName+"."+column.name() + " IS NULL");
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            }else{
                                //判断是否String类型
                                String val1 = (String) field.get(condition);
                                if("java.lang.String".equals(field.getType().getTypeName())){
                                    if(StringUtils.isBlank(val1)){
                                        //如果是String类型，且允许为null
                                        if(field.isAnnotationPresent(Empty.class)){
                                            try{
                                                //1、获取指定的属性
                                                Field fileName = entityClazz.getDeclaredField(field.getName());
                                                //2、通过getAnnotation方法获取该属性上指定的注解，注意给的参数
                                                Column column = fileName.getAnnotation(Column.class);
                                                whereSql.append(" AND "+aliasTableName+"."+column.name() + " = :"+field.getName());
                                                params.put(field.getName(),val1);
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else{
                                        try{
                                            //1、获取指定的属性
                                            Field fileName = entityClazz.getDeclaredField(field.getName());
                                            //2、通过getAnnotation方法获取该属性上指定的注解，注意给的参数
                                            Column column = fileName.getAnnotation(Column.class);
                                            whereSql.append(" AND "+aliasTableName+"."+column.name() + " = :"+field.getName());
                                            params.put(field.getName(),val1);
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }else if(field.isAnnotationPresent(Like.class)){
                            if("java.lang.String".equals(field.getType().getTypeName())){
                                String val1 = (String) field.get(condition);
                                if(StringUtils.isNotBlank(val1)){

                                    try{
                                        //1、获取指定的属性
                                        Field fileName = entityClazz.getDeclaredField(field.getName());
                                        //2、通过getAnnotation方法获取该属性上指定的注解，注意给的参数
                                        Column column = fileName.getAnnotation(Column.class);
                                        whereSql.append(" AND "+aliasTableName+"."+column.name() + " like % :"+field.getName()+ " %");
                                        params.put(field.getName(),val1);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }else if(field.isAnnotationPresent(Between.class)){
                            try {
                                Between between = field.getAnnotation(Between.class);
                                Field startField = condition.getClass().getDeclaredField(between.startAttribute());
                                Field endField = condition.getClass().getDeclaredField(between.endAttribute());
//                                startField.setAccessible(true);
//                                endField.setAccessible(true);

                                //获取值
                                Object start = startField.get(condition);
                                Object end = endField.get(condition);
                                if (start != null && end != null) {
                                    //1、获取指定的属性
                                    Field fileName = entityClazz.getDeclaredField(field.getName());
                                    //2、通过getAnnotation方法获取该属性上指定的注解，注意给的参数
                                    Column column = fileName.getAnnotation(Column.class);
                                    whereSql.append(" AND "+aliasTableName+"."+column.name() + " between "+start+ " and " + end);
                                }
                            } catch (NoSuchFieldException var12) {
                                var12.printStackTrace();
                            }
                        }else if(field.isAnnotationPresent(In.class)){
                            //要判断是否为null 或String

                            if("java.lang.String".equals(field.getType().getTypeName())){
                                String val1 = (String) field.get(condition);
                                if(StringUtils.isNotBlank(val1)){

                                    //转换in条件
                                    String[] inArr = val1.split(",");
                                    int index = 0;
                                    StringBuilder inSb = new StringBuilder("");
                                    for(String item:inArr){
                                        if(StringUtils.isNotBlank(item)){
                                            if(index==0){
                                                inSb.append("\'"+item+"\'");
                                            }else{
                                                inSb.append(",\'"+item+"\'");
                                            }
                                            index++;
                                        }
                                    }
                                    try{
                                        //1、获取指定的属性
                                        Field fileName = entityClazz.getDeclaredField(field.getName());
                                        //2、通过getAnnotation方法获取该属性上指定的注解，注意给的参数
                                        Column column = fileName.getAnnotation(Column.class);
                                        whereSql.append(" AND "+aliasTableName+"."+column.name() + " in ("+inSb.toString()+ " )");
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }else if(field.isAnnotationPresent(Condition.class)){

                            Condition con = field.getAnnotation(Condition.class);

                            String tableName = con.table();

                            String columnName = con.columnName();

                            if(StringUtils.isNotBlank(tableName)){
                                tableName = tablesName.get(tableName);
                            }else{
                                tableName = aliasTableName;
                            }

                            if(StringUtils.isBlank(columnName)){
                                try{
                                    //1、获取指定的属性
                                    Field fileName = entityClazz.getDeclaredField(field.getName());
                                    //2、通过getAnnotation方法获取该属性上指定的注解，注意给的参数
                                    Column column = fileName.getAnnotation(Column.class);
                                    columnName = column.name();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                            if(StringUtils.isNotBlank(columnName) && StringUtils.isNotBlank(tableName)){
                                if(QueryType.EQUAL == con.type()){
                                    //==
                                    if(val == null) {
                                        if (field.isAnnotationPresent(nullable.class) || con.nullable()) {
                                            whereSql.append(" AND "+tableName+"."+columnName + " IS NULL");
                                        }
                                    }else{
                                        //判断是否String类型
                                        String val1 = (String) field.get(condition);
                                        if("java.lang.String".equals(field.getType().getTypeName())){
                                            if(StringUtils.isBlank(val1)){
                                                //如果是String类型，且允许为null
                                                if(field.isAnnotationPresent(Empty.class) || con.empty()){
                                                    whereSql.append(" AND "+tableName+"."+columnName + " = :"+field.getName());
                                                    params.put(field.getName(),val1);
                                                }
                                            }else{
                                                whereSql.append(" AND "+tableName+"."+columnName + " = :"+field.getName());
                                                params.put(field.getName(),val1);
                                            }
                                        }
                                    }
                                }else if(QueryType.LIKE == con.type()){
                                    if("java.lang.String".equals(field.getType().getTypeName())){
                                        String val1 = (String) field.get(condition);
                                        if(StringUtils.isNotBlank(val1)){
                                            whereSql.append(" AND "+tableName+"."+columnName + " like % :"+field.getName()+ " %");
                                            params.put(field.getName(),val1);
                                        }
                                    }
                                }else if(QueryType.BETWEEN == con.type()){
                                    try {
                                        Field startField = condition.getClass().getDeclaredField(con.startAttribute());
                                        Field endField = condition.getClass().getDeclaredField(con.endAttribute());
                                        //startField.setAccessible(true);
                                        //endField.setAccessible(true);

                                        //获取值
                                        Object start = startField.get(condition);
                                        Object end = endField.get(condition);
                                        if (start != null && end != null) {
                                            whereSql.append(" AND "+tableName+"."+columnName + " between "+start+ " and " + end);
                                        }
                                    } catch (NoSuchFieldException var12) {
                                        var12.printStackTrace();
                                    }
                                }else if(QueryType.IN == con.type()){
                                    if("java.lang.String".equals(field.getType().getTypeName())){
                                        String val1 = (String) field.get(condition);
                                        if(StringUtils.isNotBlank(val1)){

                                            //转换in条件
                                            String[] inArr = val1.split(",");
                                            int index = 0;
                                            StringBuilder inSb = new StringBuilder("");
                                            for(String item:inArr){
                                                if(StringUtils.isNotBlank(item)){
                                                    if(index==0){
                                                        inSb.append("\'"+item+"\'");
                                                    }else{
                                                        inSb.append(",\'"+item+"\'");
                                                    }
                                                    index++;
                                                }
                                            }
                                            whereSql.append(" AND "+tableName+"."+columnName + " in ("+inSb.toString()+ " )");
                                        }
                                    }
                                }else if(QueryType.NOT_IN == con.type()){
                                    if("java.lang.String".equals(field.getType().getTypeName())){
                                        String val1 = (String) field.get(condition);
                                        if(StringUtils.isNotBlank(val1)){

                                            //转换in条件
                                            String[] inArr = val1.split(",");
                                            int index = 0;
                                            StringBuilder inSb = new StringBuilder("");
                                            for(String item:inArr){
                                                if(StringUtils.isNotBlank(item)){
                                                    if(index==0){
                                                        inSb.append("\'"+item+"\'");
                                                    }else{
                                                        inSb.append(",\'"+item+"\'");
                                                    }
                                                    index++;
                                                }
                                            }
                                            whereSql.append(" AND "+tableName+"."+columnName + " not in ("+inSb.toString()+ " )");
                                        }
                                    }
                                }
                            }

                        }else{
                            // 等值判断
                            if(val == null) {
                                if (field.isAnnotationPresent(nullable.class)) {

                                    try{
                                        //1、获取指定的属性
                                        Field fileName = entityClazz.getDeclaredField(field.getName());
                                        //2、通过getAnnotation方法获取该属性上指定的注解，注意给的参数
                                        Column column = fileName.getAnnotation(Column.class);
                                        whereSql.append(" AND "+aliasTableName+"."+column.name() + " IS NULL");
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            }else{
                                //判断是否String类型
                                String val1 = (String) field.get(condition);

                                if("java.lang.String".equals(field.getType().getTypeName())){
                                    if(StringUtils.isBlank(val1)){
                                        //如果是String类型，且允许为null
                                        if(field.isAnnotationPresent(Empty.class)){
                                            try{
                                                //1、获取指定的属性
                                                Field fileName = entityClazz.getDeclaredField(field.getName());
                                                //2、通过getAnnotation方法获取该属性上指定的注解，注意给的参数
                                                Column column = fileName.getAnnotation(Column.class);
                                                whereSql.append(" AND "+aliasTableName+"."+column.name() + " = :"+field.getName());
                                                params.put(field.getName(),val1);
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else{
                                        try{
                                            //1、获取指定的属性
                                            Field fileName = entityClazz.getDeclaredField(field.getName());
                                            //2、通过getAnnotation方法获取该属性上指定的注解，注意给的参数
                                            Column column = fileName.getAnnotation(Column.class);
                                            whereSql.append(" AND "+aliasTableName+"."+column.name() + " = :"+field.getName());
                                            params.put(field.getName(),val1);
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IllegalAccessException var13) {
                        var13.printStackTrace();
                    }
                }
            }
        });

        sql.append( " from "+ this.tableName + " " + aliasTableName + " ");

        leftJoinSqls.forEach((key,value)->{
            sql.append(" "+ value.toString() + " ");
        });

        sql.append(" "+ whereSql.toString()+" ");

        ComplexSqlWapper complexSqlWapper = new ComplexSqlWapper();

        complexSqlWapper.setSql(sql.toString());

        complexSqlWapper.setParams(params);

        return complexSqlWapper;
    }


}
