package com.lyc.simple.config;

import com.lyc.simple.jpa.AuditorAwareImpl;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author: liuyucai
 * @Created: 2023/6/12 11:22
 * @Description:
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    @Bean
    public AuditorAware<String> auditorAware() {
        AuditorAwareImpl auditorAware = new AuditorAwareImpl();
//        auditorAware.setSimpleJpaProperties(this.simpleJpaProperties);
        return auditorAware;
    }
}
