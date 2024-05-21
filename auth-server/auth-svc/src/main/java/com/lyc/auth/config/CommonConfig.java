package com.lyc.auth.config;

import com.lyc.minio.StorageClient;
import com.lyc.minio.StorageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: liuyucai
 * @Created: 2023/3/25 16:28
 * @Description:
 */
@Configuration
public class CommonConfig {

    @Bean
    @ConfigurationProperties(prefix = "minio")
    public StorageConfig storageConfig(){
        StorageConfig config = new StorageConfig();
        return config;
    }

    @Bean
    public StorageClient storageClient(@Autowired StorageConfig config){
        StorageClient client = new StorageClient();
        client.init(config);
        return client;
    }
}
