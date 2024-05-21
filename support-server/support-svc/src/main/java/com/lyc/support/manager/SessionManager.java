package com.lyc.support.manager;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.lyc.security.dto.UserDetailDTO;
import com.lyc.security.utils.SecurityContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author: liuyucai
 * @Created: 2023/6/12 10:10
 * @Description:
 */

@Component
public class SessionManager {

    @Value("${support.auth.publicKey}")
    private String publicKey;

    @Value("${support.auth.privateKey}")
    private String privateKey;

    @Autowired
    RedisTemplate redisTemplate;

    public UserDetailDTO getUser(){

        if(SecurityContextUtil.getUser() != null){
            return SecurityContextUtil.getUser();
        }else{

            String token = SecurityContextUtil.getToken();
            if(StringUtils.isNotBlank(token)){
                //先对token进行解密
                RSA rsa = new RSA(privateKey, publicKey);

                String decryptToken = rsa.decryptStr(token, KeyType.PrivateKey);

                //decryptToken的后面10位是 random, 前面是 用户名

                String userName = decryptToken.substring(0,decryptToken.length()-10);
                String random = decryptToken.substring(decryptToken.length()-10);

                UserDetailDTO userDetailDTO = (UserDetailDTO) redisTemplate.opsForValue().get(userName+":"+random);
                if (userDetailDTO != null) {
                    SecurityContextUtil.addUser(userDetailDTO);
                    return userDetailDTO;
                }
            }
            return null;
        }

    }
}
