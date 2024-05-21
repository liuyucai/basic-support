package com.lyc.simple.annotation;

import com.lyc.simple.enmus.JoinType;

import java.lang.annotation.*;

/**
 * @author lyc
 * @Created: 2023/3/27 11:11
 * @Description: 左关联
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(JoinTables.class)
public @interface JoinTable {

    String name() default "";

    /**
     * 主表的column
     * @return
     */
    String columnName() default "";

    /**
     * 关联表的 column
     * @return
     */
    String referencedColumnName() default "";

    String table() default "";

    JoinType joinType() default JoinType.LEFT;

    String nativeSql() default "";

    OnCondition [] conditions() default {};

    /**
     * 是否自动deleted =='1'
     */
    boolean autoFilterDeleted() default true;

}
