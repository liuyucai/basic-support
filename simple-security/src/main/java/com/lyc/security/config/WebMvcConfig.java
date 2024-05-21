package com.lyc.security.config;

import com.lyc.security.manager.SecuritySetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * @author: liuyucai
 * @Created: 2023/3/25 12:55
 * @Description:  处理跨域，跨域问题可用SpringSecurity处理
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Value("${support.auth.publicKey}")
    private String publicKey;

    @Value("${support.auth.privateKey}")
    private String privateKey;

    @Value("${spring.application.name:''}")
    private String applicationName;

    @Value("${lyc.security.enabled:true}")
    private Boolean enabled;

    @Value("${lyc.security.clientAuth:false}")
    private Boolean clientAuth;



    @Autowired
    SecuritySetting securitySetting;

    //先判断是否对客户端鉴权，是：判断客户端是否 有该服务的权限

    @Bean
    AuthInterceptor getRequestInterceptor(){
        return new AuthInterceptor(publicKey,privateKey,applicationName,clientAuth);
    }

    @Bean
    TokenInterceptor getTokenInterceptor(){
        return new TokenInterceptor();
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
        InterceptorRegistration tokenInterceptorRegistration = registry.addInterceptor(getTokenInterceptor());
        tokenInterceptorRegistration.addPathPatterns("/**");

        //先判断是否进行服务鉴权，是的话再加载，不是的话不用管

        //如果开启服务鉴权
        if(enabled){

            InterceptorRegistration registration = registry.addInterceptor(getRequestInterceptor());
            //addPathPatterns()方法添加需要拦截的路径
            //所有路径都被拦截
            registration.addPathPatterns("/**");

            if(securitySetting.getWhiteApis()!=null){
                registration.excludePathPatterns(
                        securitySetting.getWhiteApis()
                );
                //放行swagger的路径
                registration.excludePathPatterns(
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/*/api-docs/**",
                        "/swagger-ui.html",
                        "/doc.html"
                );
            }
        }

        //excludePathPatterns()方法添加不拦截的路径
        //添加不拦截路径,   --  读取配置文件的
//        registration.excludePathPatterns(
//                Arrays.asList(securitySetting.getWhiteApis())
////                "/captcha/**"
//        );
    }
}