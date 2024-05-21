package com.lyc.support.sql;

import com.lyc.common.loader.YamlPropertySourceFactory;
import com.lyc.common.sql.SqlBody;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author: liuyucai
 * @Created: 2023/7/27 10:35
 * @Description:
 */
@Data
@PropertySource(value = "classpath:/sql/logLogin-sql.yml", factory = YamlPropertySourceFactory.class)
@Configuration
@ConfigurationProperties(prefix = "log-login")
public class LogLoginSqlConfig {

    private SqlBody queryPageSql;
}
