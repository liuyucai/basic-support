package com.lyc.useraccount.manager;

import com.lyc.simple.utils.SQLTranslator;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author: liuyucai
 * @Created: 2023/3/11 11:43
 * @Description:
 */
@Component
public class InitStart implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //此处为项目启动后执行的方法
        SQLTranslator.setDatabase("MYSQL");
    }

}