package com.lyc.security.config;

import com.lyc.security.utils.SecurityContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author: liuyucai
 * @Created: 2023/6/2 16:33
 * @Description:
 */
@Configuration
public class SecurityRedisConfig {

    @Value("${support.auth.publicKey}")
    private String publicKey;

    @Value("${support.auth.privateKey}")
    private String privateKey;

    /**
     * 将实例注入到RedisUtil中
     */
    @Bean
    public RedisTemplate<String, Object> getRedisUtil(RedisTemplate<String, Object> redisTemplate) {
        //设置序列化Key的实例化对象
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        SecurityContextUtil.setRedisTemplate(redisTemplate);

        SecurityContextUtil.setKey(publicKey,privateKey);
        return redisTemplate;
    }
}
