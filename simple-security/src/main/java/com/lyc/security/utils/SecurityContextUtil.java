package com.lyc.security.utils;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.lyc.common.security.AuthorContext;
import com.lyc.security.dto.UserDetailDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: liuyucai
 * @Created: 2023/6/12 9:10
 * @Description:
 */
public class SecurityContextUtil {

    private static ThreadLocal<UserDetailDTO> threadLocal = new ThreadLocal();
    private static ThreadLocal<String> accessToken = new ThreadLocal();

    private static RedisTemplate<String, Object> redisTemplate;


    private static String publicKey;

    private static String privateKey;


    public static void addUser(UserDetailDTO user){
        threadLocal.set(user);
        AuthorContext.addAuthor(user.getOrgUserId());
    }
    public static void addToken(String token){
        accessToken.set(token);
    }
    public static UserDetailDTO getUser(){

        if(threadLocal.get() != null){
            return threadLocal.get();
        }else{

            //redis获取
            if(accessToken.get() != null){

                //先对token进行解密
                RSA rsa = new RSA(privateKey, publicKey);

                String decryptToken = rsa.decryptStr(accessToken.get(), KeyType.PrivateKey);

                //decryptToken的后面10位是 random, 前面是 用户名

                String userName = decryptToken.substring(0,decryptToken.length()-10);

                String random = decryptToken.substring(decryptToken.length()-10);

                UserDetailDTO userDetailDTO = (UserDetailDTO) redisTemplate.opsForValue().get("auth:access_token:"+userName+":"+random);

                if(userDetailDTO != null){
                    SecurityContextUtil.addUser(userDetailDTO);
                }

                return userDetailDTO;
            }
            return null;
        }
    }
    public static String getToken(){

        if(accessToken.get() != null){
            return accessToken.get();
        }else{
            return null;
        }
    }

    public static void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {

        SecurityContextUtil.redisTemplate = redisTemplate;
    }

    public static void setKey(String publicKey,String privateKey) {

        SecurityContextUtil.publicKey = publicKey;
        SecurityContextUtil.privateKey = privateKey;
    }


}
