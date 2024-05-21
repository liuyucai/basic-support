package com.lyc.security.manager;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/6/17 15:58
 * @Description:
 */
@Component
@ConfigurationProperties(prefix="lyc.security.wall")
@Data
public class SecuritySetting {

    private List<String> whiteApis;

    private Boolean enabled;
}
