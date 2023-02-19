package com.lyc.support;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport     // 启用springmvc对spring data的支持
public class SupportSvcApplication {
    public static void main(String[] args) {
        SpringApplication.run(SupportSvcApplication.class, args);
    }
}
