package com.lyc.support.sql;

import com.lyc.common.loader.YamlPropertySourceFactory;
import com.lyc.common.sql.SqlBody;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author: liuyucai
 * @Created: 2023/4/17 9:27
 * @Description:
 */
@Data
@PropertySource(value = "classpath:/sql/clientService-sql.yml", factory = YamlPropertySourceFactory.class)
@Configuration
@ConfigurationProperties(prefix = "client-service")
public class ClientServiceSqlConfig {

    private SqlBody queryPageSql;

}