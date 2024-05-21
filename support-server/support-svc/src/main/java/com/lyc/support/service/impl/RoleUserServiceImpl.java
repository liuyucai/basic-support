package com.lyc.support.service.impl;

import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.support.dto.RoleUserDTO;
import com.lyc.support.entity.RoleUser;
import com.lyc.support.repository.RoleUserRepository;
import com.lyc.support.service.RoleUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * @author: liuyucai
 * @Created: 2023/5/20 16:50
 * @Description:
 */
@Service
@Log4j2
public class RoleUserServiceImpl extends SimpleServiceImpl<RoleUserDTO, RoleUser,String, RoleUserRepository> implements RoleUserService {
}
