package com.lyc.common.loader;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;

/**
 * @author: liuyucai
 * @Created: 2023/3/11 12:11
 * @Description:
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String s, EncodedResource encodedResource) throws IOException {
        // 返回 yaml 属性资源
        return new YamlPropertySourceLoader()
                .load (encodedResource.getResource ().getFilename (), encodedResource.getResource ())
                .get (0);
    }
}
