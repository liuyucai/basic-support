package com.lyc.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.lyc.auth.annotation.LoginLogAnno;
import com.lyc.auth.dto.AccountLoginDTO;
import com.lyc.auth.dto.OrgUserDTO;
import com.lyc.auth.dto.RoleApiDTO;
import com.lyc.auth.entity.Client;
import com.lyc.auth.entity.UserAccount;
import com.lyc.auth.enums.ResponseCodeEnum;
import com.lyc.auth.repository.UserAccountRepository;
import com.lyc.auth.service.ClientService;
import com.lyc.auth.service.LoginService;
import com.lyc.auth.service.OrgUserService;
import com.lyc.auth.util.LoginResponseVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.security.dto.UserDetailDTO;
import com.lyc.security.utils.SecurityContextUtil;
import com.lyc.simple.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: liuyucai
 * @Created: 2023/6/2 16:12
 * @Description:
 */
@Service
@Log4j2
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    OrgUserService orgUserService;

    @Value("${support.auth.publicKey}")
    private String publicKey;

    @Value("${support.auth.privateKey}")
    private String privateKey;

    @Override
    @LoginLogAnno
    public LoginResponseVO accountLogin(AccountLoginDTO accountLoginDTO, HttpServletRequest httpServletRequest) {

        Client client = null;

        String orgUserId = null;
        try{

            //先验证客户端是否正常
            if(StringUtils.isBlank(accountLoginDTO.getClientSecret())){
                LoginResponseVO loginResponseVO = LoginResponseVO.fail(ResponseCodeEnum.CLIENT_SECRET_ERROR.getCode(),ResponseCodeEnum.CLIENT_SECRET_ERROR.getDesc());
                return loginResponseVO;
            }

            client = clientService.getByClientSecret(accountLoginDTO.getClientSecret());

            if(client == null){
                LoginResponseVO loginResponseVO = LoginResponseVO.fail(ResponseCodeEnum.CLIENT_SECRET_ERROR.getCode(),ResponseCodeEnum.CLIENT_SECRET_ERROR.getDesc());
                return loginResponseVO;
            }

            //先判断验证码是否正确
            String code = (String) redisTemplate.opsForValue().get("captcha:"+accountLoginDTO.getUuid());

            if(StringUtils.isNotBlank(accountLoginDTO.getUuid())){
                redisTemplate.delete("captcha:"+accountLoginDTO.getUuid());
            }

            if(code != null && code.equals(accountLoginDTO.getCode())){
                //判断用户账号密码
                UserAccount userAccount = userAccountRepository.getAccountInfoByUsername(accountLoginDTO.getUserName());
                if(userAccount != null){

                    String decryptPassword = "";
                    RSA rsa = new RSA(privateKey, publicKey);
                    try {
//                        RSA rsa = new RSA(privateKey, null);
                        decryptPassword = rsa.decryptStr(accountLoginDTO.getPassword(), KeyType.PrivateKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //先对密码进行MD5加密与数据库的密码进行比对
                    String encryPassword = SecureUtil.md5(decryptPassword+userAccount.getSalt());

                    //判断密码是否正确
                    if(userAccount.getPassword().equals(encryPassword)){

                        //判断账号是否被禁用
                        if(userAccount.getEnabled().intValue() !=1){
                            LoginResponseVO loginResponseVO = LoginResponseVO.fail(ResponseCodeEnum.USER_ACCOUNT_DISABLED.getCode(),ResponseCodeEnum.USER_ACCOUNT_DISABLED.getDesc());
                            loginResponseVO.setClientId(client.getId());
                            return loginResponseVO;
                        }
                        //判断账号是否失效，到了过期时间


                        //判断账号是否被锁定
                        String lock = (String) redisTemplate.opsForValue().get("userAccount:lock:"+userAccount.getUserName());

                        if(StringUtils.isNotBlank(lock)){
                            LoginResponseVO loginResponseVO = LoginResponseVO.fail(ResponseCodeEnum.USER_ACCOUNT_LOCK.getCode(),ResponseCodeEnum.USER_ACCOUNT_LOCK.getDesc());
                            loginResponseVO.setClientId(client.getId());
                            return loginResponseVO;
                        }

                        //获取机构用户信息
                        OrgUserDTO orgUserDTO = new OrgUserDTO();
                        orgUserDTO.setUserId(userAccount.getId());
                        if(StringUtils.isNotBlank(accountLoginDTO.getOrgId())){
                            orgUserDTO.setOrgId(accountLoginDTO.getOrgId());
                        }

                        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "type");
                        List<OrgUserDTO> orgUserDTOList = orgUserService.findAll(orgUserDTO,Sort.by(order));

                        if(orgUserDTOList.size()==0){
                            LoginResponseVO loginResponseVO = LoginResponseVO.fail(ResponseCodeEnum.ORG_USER_NOT_EXIST.getCode(),ResponseCodeEnum.ORG_USER_NOT_EXIST.getDesc());
                            loginResponseVO.setClientId(client.getId());
                            return loginResponseVO;
                        }else{

                            //如果有多个用户，就找主用户，如果都没有主用户，就默认第一个

                            //redis的用户信息只保留当前的用户信息，切换用户时，要进行更新

                            //如果主账号失效了，就切换到下一个
                            int effectIndex = 0;
                            //判断用户是否有效
                            if(orgUserDTOList.get(0).getEnabled().intValue() != 1){
                                if(orgUserDTOList.size()>1){
                                    for (int i =1;i<orgUserDTOList.size();i++) {
                                        if(orgUserDTOList.get(i).getEnabled().intValue() == 1){
                                            effectIndex = i;
                                            break;
                                        }
                                    }
                                }else{
                                    LoginResponseVO loginResponseVO = LoginResponseVO.fail(ResponseCodeEnum.ORG_USER_DISABLED.getCode(), ResponseCodeEnum.ORG_USER_DISABLED.getDesc());
                                    loginResponseVO.setClientId(client.getId());
                                    loginResponseVO.setOrgUserId(orgUserId);
                                    return loginResponseVO;
                                }
                            }

                            orgUserId = orgUserDTOList.get(effectIndex).getId();



                            //清楚账号的登录错误次数

                            //登录成功
                            //token：用户名+随机数  加密
                            String random = RandomUtil.randomString(10);


                            UserDetailDTO loginUserDTO1 = (UserDetailDTO) redisTemplate.opsForValue().get("auth:access_token:"+userAccount.getUserName() + ":" + random);

                            //判断redis 中 token 是否存在，防止覆盖，
                            boolean hasExit = true;
                            int time = 1;
                            if (loginUserDTO1 == null) {
                                hasExit = false;
                            }
                            while (hasExit) {
                                random = RandomUtil.randomString(10);
                                loginUserDTO1 = (UserDetailDTO) redisTemplate.opsForValue().get("auth:access_token:"+userAccount.getUserName() + ":" + random);
                                if (loginUserDTO1 == null && time < 5) {
                                    hasExit = false;
                                    time++;
                                }
                            }
                            if (time >= 5) {
                                LoginResponseVO loginResponseVO = LoginResponseVO.fail();
                                loginResponseVO.setClientId(client.getId());
                                loginResponseVO.setOrgUserId(orgUserId);
                                return loginResponseVO;
                            }

                            //返回的token  用户名+随机数  加密   账号+随机数？
                            String accessToken = Base64.getEncoder().encodeToString(rsa.encrypt(userAccount.getUserName() + random, KeyType.PublicKey));


                            //是否需要把 角色 信息存到redis里面？
                            //是否需要存 机构 信息存到redis里面？

                            //获取用户信息   账号信息+机构信息+角色信息
                            UserDetailDTO loginUserDTO = new UserDetailDTO();
                            BeanUtils.copyProperties(userAccount, loginUserDTO);

                            loginUserDTO.setOrgUserId(orgUserDTOList.get(effectIndex).getId());
                            loginUserDTO.setOrgId(orgUserDTOList.get(effectIndex).getOrgId());
                            loginUserDTO.setNickName(orgUserDTOList.get(effectIndex).getNickName());


                            Boolean bool = true;
                            //判断用户是否有该客户端的访问权限？ 账号未授权
                            //获取该用户的机构信息
                            String orgIndex = orgUserService.getOrgIndexByUserId(orgUserDTOList.get(effectIndex).getId());

                            loginUserDTO.setOrgIndex(orgIndex);

                            String[] orgList = orgIndex.split(",");
                            String inSql = "";
                            int index = 0;

                            //判断角色信息变化了没
                            for (String orgId : orgList) {
                                if (StringUtils.isNotBlank(orgId)) {
                                    if (index == 0) {
                                        inSql = "\'" + orgId + "\'";
                                    } else {
                                        inSql = inSql+",\'" + orgId + "\'";
                                    }
                                    index++;
                                }
                            }

                            //判断是否有该角色的客户端权限
                            bool = orgUserService.judgeClientAuth(orgUserDTOList.get(effectIndex).getId(), client.getId(), inSql);

                            if(!bool){
                                Map<String,Object> map = new HashMap(4);

                                LoginResponseVO loginResponseVO = LoginResponseVO.fail(ResponseCodeEnum.ORG_USER_NOT_AUTH.getCode(),ResponseCodeEnum.ORG_USER_NOT_AUTH.getDesc());
                                loginResponseVO.setClientId(client.getId());
                                loginResponseVO.setOrgUserId(orgUserId);
                                return loginResponseVO;
                            }

                            //获取用户的角色信息
                            List<String> roleList = orgUserService.getRoleInfo(orgUserDTOList.get(effectIndex).getId(),inSql);

                            loginUserDTO.setRoleList(roleList);

                            //获取用户的api权限信息  角色  ->   菜单  ->  菜单api  ->api
                            List<RoleApiDTO> apiList = orgUserService.loadUserRoleApiList(roleList);

                            Map<String,List<String>> apiMap = new HashMap<>();

                            for(RoleApiDTO roleApiDTO:apiList){
                                List<String> list = apiMap.get(roleApiDTO.getServiceName());
                                if(list == null){
                                    list = new ArrayList<>();
                                    list.add(roleApiDTO.getUrl());
                                    apiMap.put(roleApiDTO.getServiceName(),list);
                                }else{
                                    list.add(roleApiDTO.getUrl());
                                }
                            }

                            loginUserDTO.setApiMap(apiMap);

                            //在redis中添加token 用户名+随机数    //过期时间

                            long accessTokenValidity = client.getAccessTokenValidity() == null?3600:client.getAccessTokenValidity();

                            redisTemplate.opsForValue().set("auth:access_token:"+userAccount.getUserName()+":" + random, loginUserDTO,accessTokenValidity, TimeUnit.SECONDS);

                            Map<String,String> map = new HashMap<>(4);
                            map.put("accessToken",accessToken);

                            //refreshToken
                            //token 过期时间
                            //refreshToken 过期时间
                            //机构用户id？
                            map.put("orgUserId",orgUserDTOList.get(effectIndex).getId());

                            LoginResponseVO loginResponseVO = LoginResponseVO.success(map);
                            loginResponseVO.setOrgUserId(orgUserDTOList.get(effectIndex).getId());
                            loginResponseVO.setClientId(client.getId());
                            return loginResponseVO;
                        }
                    }else{
                        //获取密码错误次数
                        //账号密码错误次数+1

                        //记录账号错误的次数
                        LoginResponseVO loginResponseVO =  LoginResponseVO.fail(ResponseCodeEnum.PASSWORD_ERROR.getCode(),ResponseCodeEnum.PASSWORD_ERROR.getDesc());

                        Map<String,Object> map = new HashMap<>(4);
                        map.put("errorTimes",2);
                        loginResponseVO.setData(map);
                        loginResponseVO.setClientId(client.getId());
                        return loginResponseVO;

                    }
                }else{
                    LoginResponseVO responseVO =  LoginResponseVO.fail(ResponseCodeEnum.USER_ACCOUNT_ERROR.getCode(),ResponseCodeEnum.USER_ACCOUNT_ERROR.getDesc());
                    responseVO.setClientId(client.getId());
                    return responseVO;
                }
            }else{
                Map<String,Object> map = new HashMap(2);
                map.put("clientId",client.getId());
                LoginResponseVO responseVO =  LoginResponseVO.fail(ResponseCodeEnum.CAPTCHA_ERROR.getCode(),ResponseCodeEnum.CAPTCHA_ERROR.getDesc());
                responseVO.setClientId(client.getId());
                return responseVO;
            }
        }catch (Exception e){
            System.out.println(e);
            if(StringUtils.isNotBlank(accountLoginDTO.getUuid())){
                redisTemplate.delete("captcha:"+accountLoginDTO.getUuid());
            }
            LoginResponseVO responseVO = LoginResponseVO.fail();
            return responseVO;
        }
    }

    @Override
    public ResponseVO logout(HttpServletRequest httpServletRequest) {

        String token = httpServletRequest.getHeader("access-token");
        if(StringUtils.isNotBlank(token)){
            //解密accessToken

            //先对token进行解密
            RSA rsa = new RSA(privateKey, publicKey);

            String decryptToken = rsa.decryptStr(token, KeyType.PrivateKey);

            //decryptToken的后面10位是 random, 前面是 用户名

            String userName = decryptToken.substring(0,decryptToken.length()-10);

            String random = decryptToken.substring(decryptToken.length()-10);

            //删除token

            redisTemplate.delete("auth:access_token:"+userName+":"+random);

            return ResponseVO.success();

        }
        return ResponseVO.fail();
    }

    @Override
    public ResponseVO changeUser(HttpServletRequest httpServletRequest,String clientId,String userId) {
        String token = httpServletRequest.getHeader("access-token");

        if(StringUtils.isBlank(clientId)){
            clientId = httpServletRequest.getHeader("clientId");
        }

        //根据客户端密钥获取客户端id
        if(StringUtils.isNotBlank(token)){

            if(StringUtils.isBlank(clientId)){
                return ResponseVO.fail();
            }

            clientId = clientService.getIdByClientSecret(clientId);

            //先对token进行解密
            RSA rsa = new RSA(privateKey, publicKey);

            String decryptToken = rsa.decryptStr(token, KeyType.PrivateKey);

            //decryptToken的后面10位是 random, 前面是 用户名
            String userName = decryptToken.substring(0,decryptToken.length()-10);
            String random = decryptToken.substring(decryptToken.length()-10);

            //先获取redis的用户信息，
            UserDetailDTO loginUserDTO = (UserDetailDTO) redisTemplate.opsForValue().get("auth:access_token:"+userName+":"+random);

            if(loginUserDTO!=null){

                //判断用户信息是否一致
                if(!loginUserDTO.getOrgUserId().equals(userId)){
                    //获取用户信息，
                    OrgUserDTO orgUserDTO = orgUserService.get(userId);
                    //判断用户状态
                    if(orgUserDTO.getEnabled().intValue() != 1){
                        return ResponseVO.fail();
                    }

                    //获取用户信息   账号信息+机构信息+角色信息
//                    BeanUtils.copyProperties(userAccount, loginUserDTO);

                    loginUserDTO.setOrgUserId(orgUserDTO.getId());
                    loginUserDTO.setOrgId(orgUserDTO.getOrgId());
                    loginUserDTO.setNickName(orgUserDTO.getNickName());

                    Boolean bool = true;
                    //判断用户是否有该客户端的访问权限？ 账号未授权
                    //获取该用户的机构信息
                    String orgIndex = orgUserService.getOrgIndexByUserId(orgUserDTO.getId());

                    loginUserDTO.setOrgIndex(orgIndex);

                    String[] orgList = orgIndex.split(",");
                    String inSql = "";
                    int index = 0;

                    //判断角色信息变化了没
                    for (String orgId : orgList) {
                        if (StringUtils.isNotBlank(orgId)) {
                            if (index == 0) {
                                inSql = "\'" + orgId + "\'";
                            } else {
                                inSql = inSql+",\'" + orgId + "\'";
                            }
                            index++;
                        }
                    }

                    //判断是否有该角色的客户端权限
                    bool = orgUserService.judgeClientAuth(orgUserDTO.getId(), clientId, inSql);

                    if(!bool){
                        Map<String,Object> map = new HashMap(4);
                        map.put("clientId",clientId);
                        map.put("orgUserDTO",orgUserDTO.getId());
                        ResponseVO loginResponseVO = ResponseVO.fail(ResponseCodeEnum.ORG_USER_NOT_AUTH.getCode(),ResponseCodeEnum.ORG_USER_NOT_AUTH.getDesc());
                        loginResponseVO.setData(map);
                        return loginResponseVO;
                    }

                    //获取用户的角色信息
                    List<String> roleList = orgUserService.getRoleInfo(orgUserDTO.getId(),inSql);

                    loginUserDTO.setRoleList(roleList);

                    //获取用户的api权限信息  角色  ->   菜单  ->  菜单api  ->api
                    List<RoleApiDTO> apiList = orgUserService.loadUserRoleApiList(roleList);

                    Map<String,List<String>> apiMap = new HashMap<>();

                    for(RoleApiDTO roleApiDTO:apiList){
                        List<String> list = apiMap.get(roleApiDTO.getServiceName());
                        if(list == null){
                            list = new ArrayList<>();
                            list.add(roleApiDTO.getUrl());
                            apiMap.put(roleApiDTO.getServiceName(),list);
                        }else{
                            list.add(roleApiDTO.getUrl());
                        }
                    }

                    loginUserDTO.setApiMap(apiMap);

                    //在redis中添加token 用户名+随机数    //过期时间  修改值不用设置过期时间
//                    long accessTokenValidity = client.getAccessTokenValidity() == null?3600:client.getAccessTokenValidity();

                    redisTemplate.opsForValue().set("auth:access_token:"+userName+":" + random, loginUserDTO);

                    return ResponseVO.success();
                }
            }else{
                return ResponseVO.fail();
            }
            //删除token
            redisTemplate.delete("auth:access_token:"+userName+":"+random);

            return ResponseVO.fail();
        }
        return ResponseVO.fail();
    }
}
