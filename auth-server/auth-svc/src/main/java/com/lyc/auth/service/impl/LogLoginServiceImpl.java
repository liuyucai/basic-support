package com.lyc.auth.service.impl;

import com.lyc.auth.dto.LogLoginDTO;
import com.lyc.auth.dto.OrgUserDTO;
import com.lyc.auth.entity.LogLogin;
import com.lyc.auth.entity.OrgUser;
import com.lyc.auth.repository.LogLoginRepository;
import com.lyc.auth.repository.OrgUserRepository;
import com.lyc.auth.service.LogLoginService;
import com.lyc.auth.service.OrgUserService;
import com.lyc.simple.jpa.SimpleServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * @author: liuyucai
 * @Created: 2023/7/24 11:03
 * @Description:
 */
@Service
@Log4j2
public class LogLoginServiceImpl extends SimpleServiceImpl<LogLoginDTO, LogLogin,String, LogLoginRepository> implements LogLoginService {
}
