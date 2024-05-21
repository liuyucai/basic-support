package com.lyc.common.sql;

import lombok.Data;

/**
 * @author: liuyucai
 * @Created: 2023/3/11 11:49
 * @Description:
 */
@Data
public class SqlBody {

    private String columns;
    private String tables;
    private String condition;
    private String[] sorts;
    private String direction;

    private String order;

    private String group;
}
