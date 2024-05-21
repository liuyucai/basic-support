package com.lyc.simple.jpa;

import com.lyc.simple.dto.BaseDTO;
import com.lyc.simple.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: liuyucai
 * @Created: 2023/3/9 8:56
 * @Description:
 */
public class SimpleServiceImpl<D extends BaseDTO<ID>,T extends BaseEntity<ID>,ID,REPOSITORY extends BaseRepository<T, ID>> extends BaseServiceImpl<D, T, ID> {

    private REPOSITORY repository;

    @Override
    public REPOSITORY getRepository() {
        return this.repository;
    }

    @Autowired
    public void setRepository(REPOSITORY repository) {
        this.repository = repository;
    }


}
