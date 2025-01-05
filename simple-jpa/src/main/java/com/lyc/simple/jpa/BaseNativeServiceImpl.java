package com.lyc.simple.jpa;

import com.lyc.simple.annotation.*;
import com.lyc.simple.annotation.JoinColumn;
import com.lyc.simple.annotation.JoinTable;
import com.lyc.simple.enmus.JoinType;
import com.lyc.simple.enmus.QueryType;
import com.lyc.simple.entity.PrimaryKeyEntity;
import com.lyc.simple.utils.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: liuyucai
 * @Created: 2024/5/28 15:45
 * @Description:
 */
@Log4j2
public abstract class BaseNativeServiceImpl<ID> implements BaseNativeService<ID>{

    private Class entityClazz;
    private String tableName;
    private String primaryKeyName;

    public void setEntityClazz(Class entityClazz){
        this.entityClazz = entityClazz;
    }

    public void setTableName(String tableName){
        this.tableName = tableName;
    }

    public void setPrimaryKeyName(String primaryKeyName){
        this.primaryKeyName = primaryKeyName;
    }

    /**
     * @PersistenceContext是jpa专有的注解，而@Autowired是spring自带的注释
     * EntityManager不是线程安全的，当多个请求进来的时候，spring会创建多个线程，
     * @PersistenceContext就是用来为每个线程创建一个EntityManager的，而@Autowired只创建了一个，为所有线程共用，有可能报错
     *
     */
    @PersistenceContext
    private EntityManager entityManager;

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

            OnCondition[] conditions = joinTable1.conditions();
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
                    if(field.isAnnotationPresent(com.lyc.simple.annotation.JoinColumn.class)){
                        com.lyc.simple.annotation.JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
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
