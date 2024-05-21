package com.lyc.support.service;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.BaseService;
import com.lyc.support.dto.*;
import com.lyc.support.entity.UserAccount;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/2/22 8:54
 * @Description:
 */
public interface UserAccountService extends BaseService<UserAccountDTO, UserAccount,String> {
    List<UserAccount> getUserInfo();

    ResponseVO<UserAccountSaveInfoDTO> saveUserInfo(UserAccountSaveInfoDTO userAccountSaveInfoDTO);

    ResponseVO<UserAccountSaveInfoDTO> updateUserInfo(UserAccountSaveInfoDTO userAccountSaveInfoDTO);

    Page<UserAccountPageRespDTO> getPageList(PageRequestVO<UserAccountPageReqDTO> page);

    ResponseVO<UserAccountDetailDTO> getUserDetailById(String id);

    ResponseVO updatePassword(UpdatePasswordDTO updatePasswordDTO);

    ResponseVO deleteAccount(String id);

    ResponseVO resetPassword(String id);

    ResponseVO bindPhone(BindPhoneDTO bindPhoneDTO);

    ResponseVO unbindPhone(String id);

    ResponseVO bindEmail(BindEmailDTO bindEmailDTO);

    ResponseVO unbindEmail(String id);
}
