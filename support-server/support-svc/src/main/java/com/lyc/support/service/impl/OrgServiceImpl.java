package com.lyc.support.service.impl;

import com.lyc.support.repository.OrgRespository;
import com.lyc.support.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: liuyucai
 * @Created: 2023/3/5 18:31
 * @Description:
 */
@Service
public class OrgServiceImpl implements OrgService {

    @Autowired
    OrgRespository orgRespository;
}
