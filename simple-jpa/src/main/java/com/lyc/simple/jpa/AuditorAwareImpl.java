package com.lyc.simple.jpa;

import com.lyc.common.security.AuthorContext;
import com.lyc.simple.utils.StringUtils;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author: liuyucai
 * @Created: 2023/6/12 11:07
 * @Description:
 */
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {


        String author = AuthorContext.getAuthor();

        if(StringUtils.isNotBlank(author)){
            //获取用户id,并返回
            return Optional.of(author);
        }else{
            //获取用户id,并返回
            return Optional.of("anonymous");
        }
    }
}
