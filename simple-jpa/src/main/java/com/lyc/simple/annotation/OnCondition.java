package com.lyc.simple.annotation;

import com.lyc.simple.enmus.OnType;

import java.lang.annotation.*;

/**
 * @author lyc
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OnCondition {

    /**
     * 关联的列
     */
    String columnName() default "";

    /**
     * 与之关联的列，当 type = COLUMN时才有效
     */
    String referencedColumnName() default "";

//    /**
//     * 关联值  当 type = COLUMN时才有效
//     */
//    String value() default "";
//
//    /**
//     * 关联的类型，VALUE:值关联，COLUMN:列关联
//     * @return
//     */
//    OnType type() default OnType.VALUE;

}
