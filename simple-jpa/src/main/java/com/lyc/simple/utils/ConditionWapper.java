package com.lyc.simple.utils;

import com.lyc.simple.annotation.Equal;
import lombok.Data;

import java.lang.reflect.Type;

/**
 * @author: liuyucai
 * @Created: 2023/3/26 20:52
 * @Description:
 */
@Data
public class ConditionWapper {

    private Object value;
    private Type dataType;
    private String fieldName;
    private Class<?> conditionType = Equal.class;

    private Object startValue;
    private Object endValue;
}
