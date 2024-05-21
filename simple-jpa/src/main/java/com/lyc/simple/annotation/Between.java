package com.lyc.simple.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lyc
 * @Created: 2023/3/28 09:03
 * @Description: between 查询条件
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Between {

    String entityColumnName() default "";

    String startAttribute() default "";

    String endAttribute() default "";
}
