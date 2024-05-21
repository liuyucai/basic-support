package com.lyc.simple.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lyc
 * @Created: 2023/3/28 09:03
 * @Description: 标记该字段是关联查询的on条件
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface On {

    String table() default "";

    String sql() default "";

    String condition() default "";

}
