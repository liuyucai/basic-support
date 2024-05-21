package com.lyc.auth.repository;

import com.lyc.auth.entity.UserAccount;
import com.lyc.simple.jpa.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/2/22 8:58
 * @Description:
 */
public interface UserAccountRepository extends BaseRepository<UserAccount,String> {

    /**
     * 根据账号获取账号信息
     * @param userName
     * @return
     */
    @Query(value = "SELECT u  FROM UserAccount u WHERE u.deleted = 0 and (u.userName= :userName or u.phoneNo =:userName)")
    UserAccount getAccountInfoByUsername(@Param("userName") String userName);
}
