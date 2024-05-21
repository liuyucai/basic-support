package com.lyc.support.repository;

import com.lyc.simple.jpa.BaseRepository;
import com.lyc.support.entity.UserAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author: liuyucai
 * @Created: 2023/2/22 8:58
 * @Description:
 */
public interface UserAccountRepository extends BaseRepository<UserAccount,String> {


    /**
     * 判断该账号是否存在
     * @param userName
     * @return
     */
    @Query(value = "SELECT count(1) as countNumber FROM sys_user_account WHERE DELETED = 0 AND user_name = :userName", nativeQuery = true)
    Integer getNumberByUserName(@Param("userName") String userName);


    /**
     * 判断该手机号是否存在
     * @param phoneNo
     * @return
     */
    @Query(value = "SELECT count(1) as countNumber FROM sys_user_account WHERE DELETED = 0 AND phone_no = :phoneNo", nativeQuery = true)
    Integer getNumberByPhoneNo(@Param("phoneNo") String phoneNo);


    /**
     * 判断该证件号是否存在
     * @param identityNo
     * @return
     */
    @Query(value = "SELECT count(1) as countNumber FROM sys_user_account WHERE DELETED = 0 AND identity_no = :identityNo", nativeQuery = true)
    Integer getNumberByIdentityNo(@Param("identityNo") String identityNo);

    /**
     * 获取用户密码
     * @param id
     * @return
     */
    @Query(value = "SELECT password FROM sys_user_account WHERE id = :id", nativeQuery = true)
    String getPasswordById(@Param("id")String id);

    /**
     * 判断该证件号是否存在
     * @param email
     * @return
     */
    @Query(value = "SELECT count(1) as countNumber FROM sys_user_account WHERE DELETED = 0 AND email = :email", nativeQuery = true)
    Integer getNumberByEmail(@Param("email") String email);
}
