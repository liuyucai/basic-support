package com.lyc.simple.utils;

import lombok.Data;

import java.util.Map;

/**
 * @author: liuyucai
 * @Created: 2023/4/8 21:50
 * @Description:
 */
@Data
public class ComplexSqlWapper {

    private String sql;

    private Map<String,Object> params;
}
