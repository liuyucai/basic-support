package com.lyc.auth.service.impl;

import com.lyc.auth.dto.UserAccountDTO;
import com.lyc.auth.entity.UserAccount;
import com.lyc.auth.repository.UserAccountRepository;
import com.lyc.auth.service.UserAccountService;
import com.lyc.simple.jpa.SimpleServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/2/22 8:54
 * @Description:
 */
@Service
@Log4j2
public class UserAccountServiceImpl extends SimpleServiceImpl<UserAccountDTO, UserAccount,String, UserAccountRepository> implements UserAccountService {

    @Autowired
    UserAccountRepository userAccountRepository;

}
