package com.lyc.simple.utils;

import com.lyc.simple.enmus.QueryType;
import lombok.Data;

/**
 * @author: liuyucai
 * @Created: 2023/4/23 14:46
 * @Description:
 */
@Data
public class PredicateItem {

    public PredicateItem(){

    }
    public PredicateItem(String name,String column,Object value){
        this.name = name;
        this.column = column;
        this.value = value;
        this.type = QueryType.EQUAL;
    }
    public PredicateItem(String name,String column,Object value,QueryType type){
        this.name = name;
        this.column = column;
        this.value = value;
        this.type = type;
    }

    private String name;

    private String column;

    private Object value;

    private Object startValue;

    private Object endValue;

    private QueryType type = QueryType.EQUAL;
}
