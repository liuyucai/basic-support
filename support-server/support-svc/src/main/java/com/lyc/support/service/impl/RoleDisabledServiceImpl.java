package com.lyc.support.service.impl;

import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.support.dto.RoleDisabledDTO;
import com.lyc.support.dto.RoleResourceDTO;
import com.lyc.support.entity.RoleDisabled;
import com.lyc.support.entity.RoleResource;
import com.lyc.support.repository.RoleDisabledRepository;
import com.lyc.support.repository.RoleResourceRepository;
import com.lyc.support.service.RoleDisabledService;
import com.lyc.support.service.RoleResourceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * @author: liuyucai
 * @Created: 2023/5/28 15:23
 * @Description:
 */
@Service
@Log4j2
public class RoleDisabledServiceImpl extends SimpleServiceImpl<RoleDisabledDTO, RoleDisabled,String, RoleDisabledRepository> implements RoleDisabledService {
}
