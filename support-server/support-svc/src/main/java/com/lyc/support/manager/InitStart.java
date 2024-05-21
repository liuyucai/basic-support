package com.lyc.support.manager;

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

        System.out.println("database:MYSQL");
        //此处为项目启动完成后，执行的方法
        SQLTranslator.setDatabase("MYSQL");

        //去数据库中，加载
    }

}