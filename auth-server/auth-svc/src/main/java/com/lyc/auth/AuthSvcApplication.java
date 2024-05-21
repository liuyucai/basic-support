package com.lyc.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * @author: liuyucai
 * @Created: 2023/3/11 16:34
 * @Description:
 */
@SpringBootApplication(scanBasePackages = {"com.lyc.simple.*","com.lyc.auth.*","com.lyc.security.*"})
@EntityScan(
        basePackages = {
                "com.lyc.auth.*",
                "com.lyc.security.entity"
        }
)

@EnableJpaRepositories(
        basePackages = {
                "com.lyc.auth.*",
                "com.lyc.security.*"
        }
)

@EnableSpringDataWebSupport     // 启用springmvc对spring data的支持
public class AuthSvcApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthSvcApplication.class, args);
    }
}
