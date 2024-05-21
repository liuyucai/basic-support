package com.lyc.support.sql;

import com.lyc.common.loader.YamlPropertySourceFactory;
import com.lyc.common.sql.SqlBody;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author: liuyucai
 * @Created: 2023/10/7 8:57
 * @Description:
 */
@Data
@PropertySource(value = "classpath:/sql/clientMenu1-sql.yml", factory = YamlPropertySourceFactory.class)
@Configuration
@ConfigurationProperties(prefix = "client-menu1")
public class ClientMenu1SqlConfig {

    private SqlBody queryUserMenuSql;


}
