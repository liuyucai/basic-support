package com.lyc.support.sql;

import com.lyc.common.loader.YamlPropertySourceFactory;
import com.lyc.common.sql.SqlBody;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author: liuyucai
 * @Created: 2023/5/13 16:12
 * @Description:
 */
@Data
@PropertySource(value = "classpath:/sql/roleResource1-sql.yml", factory = YamlPropertySourceFactory.class)
@Configuration
@ConfigurationProperties(prefix = "role-resource1")
public class RoleResource1SqlConfig {

    private SqlBody queryClientListSql;

    private SqlBody queryMenuListSql;

    private SqlBody queryRouterListSql;

    private SqlBody queryFunctionListSql;
}
