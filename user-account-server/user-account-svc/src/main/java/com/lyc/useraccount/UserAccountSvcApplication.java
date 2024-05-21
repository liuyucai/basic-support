package com.lyc.useraccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * @author: liuyucai
 * @Created: 2023/5/29 8:43
 * @Description:
 */
@SpringBootApplication(scanBasePackages = {"com.lyc.simple.*","com.lyc.useraccount.*","com.lyc.security.*"})

@EntityScan(
        basePackages = {
                "com.lyc.useraccount.*",
                "com.lyc.security.entity"
        }
)

@EnableJpaRepositories(
        basePackages = {
                "com.lyc.useraccount.*",
                "com.lyc.security.*"
        }
)
@EnableSpringDataWebSupport     // 启用springmvc对spring data的支持
public class UserAccountSvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserAccountSvcApplication.class, args);
    }
}
