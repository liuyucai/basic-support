package com.lyc.simple.dto;

/**
 * @author: liuyucai
 * @Created: 2023/3/10 8:36
 * @Description:
 */
public abstract class BaseDTO<ID> {

    public BaseDTO() {
    }

    public abstract ID getPrimaryKey();

    public abstract void setPrimaryKey(ID id);

}
