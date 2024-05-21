package com.lyc.simple.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lyc
 * @Created: 2023/3/27 11:11
 * @Description: 关联表的查询字段
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinColumn {

    String name() default "";

    String tableName() default "";

    String entityName() default "";

}
