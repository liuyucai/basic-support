package com.lyc.auth.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginLogAnno {

    String value() default "";

    String type() default "";

    String method() default "";
}
