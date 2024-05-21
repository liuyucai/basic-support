package com.lyc.useraccount.controller;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.lyc.common.vo.ResponseVO;
import com.lyc.security.dto.UserDetailDTO;
import com.lyc.useraccount.dto.UserInfoDTO;
import com.lyc.useraccount.enums.ResponseCodeEnum;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author: liuyucai
 * @Created: 2023/6/8 10:23
 * @Description:
 */
@Log4j2
@Api(value = "账号信息API", tags = {"账号信息CURD"})
@RestController
@RequestMapping("/userAccount")
public class UserAccountController {

    @Value("${support.auth.publicKey}")
    private String publicKey;

    @Value("${support.auth.privateKey}")
    private String privateKey;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/getUserInfo")
    public ResponseVO<UserInfoDTO> getUserInfo(@RequestHeader("access-token") String accessToken){

        try{
            if(StringUtils.isNotBlank(accessToken)){

                //先对token进行解密
                RSA rsa = new RSA(privateKey, publicKey);

                String decryptToken = rsa.decryptStr(accessToken, KeyType.PrivateKey);

                //decryptToken的后面10位是 random, 前面是 用户名
                String userName = decryptToken.substring(0,decryptToken.length()-10);
                String random = decryptToken.substring(decryptToken.length()-10);

                //判断token  根据token获取用户信息
                UserDetailDTO loginUserDTO = (UserDetailDTO) redisTemplate.opsForValue().get("auth:access_token:"+userName+":"+random);

                if(loginUserDTO !=null){
                    UserInfoDTO userInfoDTO = new UserInfoDTO();
                    //获取用户信息
                    BeanUtils.copyProperties(loginUserDTO,userInfoDTO);

                    return ResponseVO.success(userInfoDTO);

                }else{
                    return ResponseVO.fail(ResponseCodeEnum.TOKEN_ERROR.getCode(),ResponseCodeEnum.TOKEN_ERROR.getDesc());
                }
            }else{
                return ResponseVO.fail(ResponseCodeEnum.GET_TOKEN_ERROR.getCode(),ResponseCodeEnum.GET_TOKEN_ERROR.getDesc());
            }
        }catch (Exception e){
            return ResponseVO.fail();
        }
    }
}
