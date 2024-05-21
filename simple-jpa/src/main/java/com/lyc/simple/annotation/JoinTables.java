package com.lyc.simple.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lyc
 *
 * @Repeatable 注解是用于声明其它类型注解的元注解，来表示这个声明的注解是可重复的。@Repeatable的值是另一个注解，其可以通过这个另一个注解的值来包含这个可重复的注解。
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinTables {
    JoinTable[] value() default {};
}
