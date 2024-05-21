package com.lyc.simple.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/3/8 8:49
 * @Description:
 */
public abstract class PrimaryKeyEntity<ID> implements Serializable {

    public PrimaryKeyEntity() {
    }

    public abstract ID getPrimaryKey();

    public abstract void setPrimaryKey(ID id);
}
