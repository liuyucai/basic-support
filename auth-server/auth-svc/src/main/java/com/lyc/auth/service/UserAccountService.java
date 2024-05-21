package com.lyc.auth.service;

import com.lyc.auth.dto.UserAccountDTO;
import com.lyc.auth.entity.UserAccount;
import com.lyc.simple.jpa.BaseService;
/**
 * @author: liuyucai
 * @Created: 2023/2/22 8:54
 * @Description:
 */
public interface UserAccountService extends BaseService<UserAccountDTO, UserAccount,String> {

}
