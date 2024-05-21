package com.lyc.support.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.security.dto.UserDetailDTO;
import com.lyc.security.utils.SecurityContextUtil;
import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.simple.utils.PredicateItem;
import com.lyc.support.dto.*;
import com.lyc.support.emun.ResponseCodeEnum;
import com.lyc.support.entity.UserAccount;
import com.lyc.support.repository.UserAccountRepository;
import com.lyc.support.service.OrgUserService;
import com.lyc.support.service.UserAccountService;
import com.lyc.support.sql.UserSqlConfig;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    @Autowired
    OrgUserService orgUserService;

    @Autowired
    UserSqlConfig userSqlConfig;

    @Value("${support.auth.privateKey}")
    private String privateKey;

    @Value("${support.auth.publicKey}")
    private String publicKey;

    @Value("${lyc.defaultPwd:123456}")
    private String defaultPwd;

    @Override
    public List<UserAccount> getUserInfo() {
        return userAccountRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<UserAccountSaveInfoDTO> saveUserInfo(UserAccountSaveInfoDTO userAccountSaveInfoDTO) {

        try{

            if(StringUtils.isBlank(userAccountSaveInfoDTO.getUserName())){
                return ResponseVO.fail(ResponseCodeEnum.USER_NAME_EMPTY_ERROR.getCode(),ResponseCodeEnum.USER_NAME_EMPTY_ERROR.getDesc());
            }
            //先判断该账号是否已存在
            Integer number = userAccountRepository.getNumberByUserName(userAccountSaveInfoDTO.getUserName());

            if(number.intValue()>0){
                return ResponseVO.fail(ResponseCodeEnum.USER_NAME_EXIST_ERROR.getCode(),ResponseCodeEnum.USER_NAME_EXIST_ERROR.getDesc());
            }

            //判断手机号是否存在
            if(StringUtils.isNotBlank(userAccountSaveInfoDTO.getPhoneNo())){
                number = userAccountRepository.getNumberByPhoneNo(userAccountSaveInfoDTO.getPhoneNo());
                if(number.intValue()>0){
                    return ResponseVO.fail(ResponseCodeEnum.PHONE_NO_EXIST_ERROR.getCode(),ResponseCodeEnum.PHONE_NO_EXIST_ERROR.getDesc());
                }
            }

            //判断身份证是否存在
            if(StringUtils.isNotBlank(userAccountSaveInfoDTO.getIdentityNo())){
                number = userAccountRepository.getNumberByIdentityNo(userAccountSaveInfoDTO.getIdentityNo());
                if(number.intValue()>0){
                    return ResponseVO.fail(ResponseCodeEnum.IDENTITY_NO_EXIST_ERROR.getCode(),ResponseCodeEnum.IDENTITY_NO_EXIST_ERROR.getDesc());
                }
            }

            //判断邮箱是否存在
            if(StringUtils.isNotBlank(userAccountSaveInfoDTO.getEmail())){
                number = userAccountRepository.getNumberByEmail(userAccountSaveInfoDTO.getEmail());
                if(number.intValue()>0){
                    return ResponseVO.fail(ResponseCodeEnum.EMAIL_EXIST_ERROR.getCode(),ResponseCodeEnum.EMAIL_EXIST_ERROR.getDesc());
                }
            }

            //添加用户信息
            UserAccountDTO userAccountDTO = new UserAccountDTO();
            BeanUtils.copyProperties(userAccountSaveInfoDTO, userAccountDTO);

            String salt = UUID.randomUUID().toString();
            userAccountDTO.setSalt(salt);

            //对密码进行加密
            String decryptPassword = "";
            try {
                RSA rsa = new RSA(privateKey, null);
                decryptPassword = rsa.decryptStr(userAccountDTO.getPassword(), KeyType.PrivateKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
            userAccountDTO.setPassword(SecureUtil.md5(decryptPassword+salt));
            this.save(userAccountDTO);
            userAccountSaveInfoDTO.setId(userAccountDTO.getId());
            return ResponseVO.success(userAccountSaveInfoDTO);

        }catch (Exception e){
            return ResponseVO.fail();
        }
    }

    @Override
    public ResponseVO<UserAccountSaveInfoDTO> updateUserInfo(UserAccountSaveInfoDTO userAccountSaveInfoDTO) {

        //先根据id获取信息
        UserAccountDTO userAccountDTO = this.get(userAccountSaveInfoDTO.getId());

        if(userAccountDTO != null){

            //判断用户名是否一样
            if(StringUtils.isNotBlank(userAccountSaveInfoDTO.getUserName())){
                if(!userAccountSaveInfoDTO.getUserName().equals(userAccountDTO.getUserName())){
                    //先判断该账号是否已存在
                    Integer number = userAccountRepository.getNumberByUserName(userAccountSaveInfoDTO.getUserName());
                    if(number.intValue()>0){
                        return ResponseVO.fail(ResponseCodeEnum.USER_NAME_EXIST_ERROR.getCode(),ResponseCodeEnum.USER_NAME_EXIST_ERROR.getDesc());
                    }
                }
            }else{
                return ResponseVO.fail(ResponseCodeEnum.USER_NAME_EMPTY_ERROR.getCode(),ResponseCodeEnum.USER_NAME_EMPTY_ERROR.getDesc());
            }

            //判断手机号
            if(StringUtils.isNotBlank(userAccountSaveInfoDTO.getPhoneNo())){
                if(!userAccountSaveInfoDTO.getPhoneNo().equals(userAccountDTO.getPhoneNo())){
                    //先判断该账号是否已存在
                    Integer number = userAccountRepository.getNumberByPhoneNo(userAccountSaveInfoDTO.getPhoneNo());
                    if(number.intValue()>0){
                        return ResponseVO.fail(ResponseCodeEnum.PHONE_NO_EXIST_ERROR.getCode(),ResponseCodeEnum.PHONE_NO_EXIST_ERROR.getDesc());
                    }
                }
            }

            //判断证件号码
            if(StringUtils.isNotBlank(userAccountSaveInfoDTO.getIdentityNo())){
                if(!userAccountSaveInfoDTO.getIdentityNo().equals(userAccountDTO.getIdentityNo())){
                    //先判断该账号是否已存在
                    Integer number = userAccountRepository.getNumberByIdentityNo(userAccountSaveInfoDTO.getIdentityNo());
                    if(number.intValue()>0){
                        return ResponseVO.fail(ResponseCodeEnum.IDENTITY_NO_EXIST_ERROR.getCode(),ResponseCodeEnum.IDENTITY_NO_EXIST_ERROR.getDesc());
                    }
                }
            }

            //判断邮箱
            if(StringUtils.isNotBlank(userAccountSaveInfoDTO.getEmail())){
                if(!userAccountSaveInfoDTO.getEmail().equals(userAccountDTO.getEmail())){
                    //先判断该账号是否已存在
                    Integer number = userAccountRepository.getNumberByEmail(userAccountSaveInfoDTO.getEmail());
                    if(number.intValue()>0){
                        return ResponseVO.fail(ResponseCodeEnum.EMAIL_EXIST_ERROR.getCode(),ResponseCodeEnum.EMAIL_EXIST_ERROR.getDesc());
                    }
                }
            }

            //密码不能修改
            userAccountSaveInfoDTO.setPassword(userAccountDTO.getPassword());

            this.update(userAccountSaveInfoDTO);
            return ResponseVO.success();
        }

        return  ResponseVO.fail();
    }

    @Override
    public Page<UserAccountPageRespDTO> getPageList(PageRequestVO<UserAccountPageReqDTO> page) {


        UserAccountDTO userAccountDTO = new UserAccountDTO();

        BeanUtils.copyProperties(page.getCondition(), userAccountDTO);

        PageRequest pageRequest =  PageRequest.of(page.getPage() - 1, page.getSize());

        Page<UserAccountPageRespDTO> result = this.findPage(userAccountDTO,pageRequest, UserAccountPageRespDTO.class);

        return result;
    }


    @Override
    public ResponseVO<UserAccountDetailDTO> getUserDetailById(String id) {

        //获取用户基本信息

        UserAccountDTO userAccountDTO = this.get(id);

        UserAccountDetailDTO userAccountDetailDTO = new UserAccountDetailDTO();

        BeanUtils.copyProperties(userAccountDTO,userAccountDetailDTO);

        if(StringUtils.isNotBlank(userAccountDTO.getPassword())){
            userAccountDetailDTO.setHasPassword(1);
        }else{
            userAccountDetailDTO.setHasPassword(0);
        }
        //获取机构信息

        //获取角色信息
        return ResponseVO.success(userAccountDetailDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO updatePassword(UpdatePasswordDTO updatePasswordDTO) {

        // 获取 账号id
        if(StringUtils.isBlank(updatePasswordDTO.getId())){
            //从登录信息里面获取
            UserDetailDTO userDetailDTO = SecurityContextUtil.getUser();
            if(userDetailDTO != null){
                updatePasswordDTO.setId(userDetailDTO.getId());
            }else{
                return ResponseVO.fail();
            }
        }
        //获取用户信息
        UserAccountDTO userAccountDTO = this.get(updatePasswordDTO.getId());
        if(userAccountDTO != null){
            String decryptPassword = "";
            RSA rsa = new RSA(privateKey, publicKey);
            try {
                decryptPassword = rsa.decryptStr(updatePasswordDTO.getOldPassword(), KeyType.PrivateKey);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //先对密码进行MD5加密与数据库的密码进行比对
            String encryPassword = SecureUtil.md5(decryptPassword+userAccountDTO.getSalt());
            //判断原密码是否正确
            if(userAccountDTO.getPassword().equals(encryPassword)){

                //先对新密码和确认密码进行解密
                //对新密码进行解密
                String newPassword = "";
                String confirmPassword = "";
                try {
                    newPassword = rsa.decryptStr(updatePasswordDTO.getNewPassword(), KeyType.PrivateKey);
                    confirmPassword = rsa.decryptStr(updatePasswordDTO.getConfirmPassword(), KeyType.PrivateKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                //判断新密码和确认密码是否一致
                if(newPassword.equals(confirmPassword) && StringUtils.isNotBlank(newPassword)){


                    //对新密码加密
                    String salt = UUID.randomUUID().toString();

                    //对新密码进行加密
                    userAccountDTO.setPassword(SecureUtil.md5(newPassword+salt));
                    userAccountDTO.setSalt(salt);
                    //保存
                    this.update(userAccountDTO);
                    return ResponseVO.success();
                }
            }
        }

        //新密码加密保存
        return ResponseVO.fail();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO deleteAccount(String id) {

        //删除账号信息

        this.delete(id);

        PredicateItem predicateItem = new PredicateItem();

        predicateItem.setName("userId");
        predicateItem.setColumn("user_id");
        predicateItem.setValue(id);
        orgUserService.delete(predicateItem);
        //删除该账号的用户信息
        return ResponseVO.success();
    }

    @Override
    public ResponseVO resetPassword(String id) {

        //先判断密码规则

        String pwd = defaultPwd;

        UserAccountDTO userAccountDTO = this.get(id);

        if(pwd.indexOf("$userName") > -1){
            pwd = pwd.replaceAll("$userName",userAccountDTO.getUserName());
        }

        if(StringUtils.isNotBlank(pwd)){

            //对密码进行加密
//            String decryptPassword = "";
            try {
                RSA rsa = new RSA(privateKey, null);
//                decryptPassword = rsa.decryptStr(pwd, KeyType.PrivateKey);
                userAccountDTO.setPassword(SecureUtil.md5(pwd+userAccountDTO.getSalt()));
                this.update(userAccountDTO);
                return ResponseVO.success();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseVO.fail();
    }

    @Override
    public ResponseVO bindPhone(BindPhoneDTO bindPhoneDTO) {

        //先判断该手机号是否已绑定
        if(StringUtils.isNotBlank(bindPhoneDTO.getPhoneNo())){
            Integer number = userAccountRepository.getNumberByPhoneNo(bindPhoneDTO.getPhoneNo());
            if(number.intValue()>0){
                return ResponseVO.fail(ResponseCodeEnum.PHONE_NO_EXIST_ERROR.getCode(),ResponseCodeEnum.PHONE_NO_EXIST_ERROR.getDesc());
            }else{
                this.update(bindPhoneDTO);
            }
        }else{
            return ResponseVO.fail(ResponseCodeEnum.PHONE_NO_EMPTY_ERROR.getCode(),ResponseCodeEnum.PHONE_NO_EMPTY_ERROR.getDesc());
        }
        return ResponseVO.success();
    }

    @Override
    public ResponseVO unbindPhone(String id) {

        BindPhoneDTO bindPhoneDTO = new BindPhoneDTO();
        bindPhoneDTO.setId(id);
        this.update(bindPhoneDTO);
        return ResponseVO.success();
    }

    @Override
    public ResponseVO bindEmail(BindEmailDTO bindEmailDTO) {
        //先判断该手机号是否已绑定
        if(StringUtils.isNotBlank(bindEmailDTO.getEmail())){
            Integer number = userAccountRepository.getNumberByEmail(bindEmailDTO.getEmail());
            if(number.intValue()>0){
                return ResponseVO.fail(ResponseCodeEnum.EMAIL_EXIST_ERROR.getCode(),ResponseCodeEnum.EMAIL_EXIST_ERROR.getDesc());
            }else{
                this.update(bindEmailDTO);
            }
        }else{
            return ResponseVO.fail(ResponseCodeEnum.EMAIL_EMPTY_ERROR.getCode(),ResponseCodeEnum.EMAIL_EMPTY_ERROR.getDesc());
        }
        return ResponseVO.success();
    }

    @Override
    public ResponseVO unbindEmail(String id) {

        BindEmailDTO bindEmailDTO = new BindEmailDTO();
        bindEmailDTO.setId(id);
        this.update(bindEmailDTO);
        return ResponseVO.success();
    }
}
