package com.lyc.useraccount.config;

import com.lyc.useraccount.config.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: liuyucai
 * @Created: 2023/3/25 12:55
 * @Description:  处理跨域，跨域问题可用SpringSecurity处理
 */
//@Configuration
public class WebMvcConfig2 implements WebMvcConfigurer {


    @Value("${support.auth.publicKey}")
    private String publicKey;

    @Value("${support.auth.privateKey}")
    private String privateKey;

    @Bean
    RequestInterceptor getRequestInterceptor(){
        return new RequestInterceptor(publicKey,privateKey);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 可以被跨域请求的接口，/**此时表示所有
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*")
                //需要带cookie等凭证时，设置为true，就会把cookie的相关信息带上
                .allowCredentials(false)
                .exposedHeaders("")
                .maxAge(3600);
    }

    /**
     * 重写addInterceptors()实现拦截器
     * 配置：要拦截的路径以及不拦截的路径
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册Interceptor拦截器(Interceptor这个类是我们自己写的拦截器类)
        InterceptorRegistration registration = registry.addInterceptor(getRequestInterceptor());
        //addPathPatterns()方法添加需要拦截的路径
        //所有路径都被拦截
        registration.addPathPatterns("/**");

        //excludePathPatterns()方法添加不拦截的路径
        //添加不拦截路径
        registration.excludePathPatterns(
                "/userAccount/getUserInfo"
        );

    }
}