package com.lyc.simple.annotation;

import com.lyc.simple.enmus.QueryType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author lyc
 * @Created: 2023/3/28 09:03
 * @Description: 标记该字段是查询条件
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {

    String columnName() default "";

    String table() default "";

    QueryType type() default QueryType.EQUAL;

    String startAttribute() default "";

    String endAttribute() default "";

    boolean nullable() default false;

    boolean empty() default false;
}
