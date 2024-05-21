package com.lyc.support;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * @author lyc
 */

@SpringBootApplication(scanBasePackages = {"com.lyc.support.*","com.lyc.simple.*","com.lyc.security.*"})
@EntityScan(
        basePackages = {
                "com.lyc.support.*",
                "com.lyc.security.entity"
        }
)
//                "com.lyc.simple.*",

@EnableJpaRepositories(
        basePackages = {
//                "com.lyc.simple.*",
                "com.lyc.support.*",
                "com.lyc.security.*"
        }
)
//@EnableSpringDataWebSupport     // 启用springmvc对spring data的支持
public class SupportSvcApplication {
    public static void main(String[] args) {
        SpringApplication.run(SupportSvcApplication.class, args);
    }
}
