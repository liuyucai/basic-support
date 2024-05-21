package com.lyc.useraccount.config;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author: liuyucai
 * @Created: 2023/6/9 11:57
 * @Description:
 */
public class ExcludePatternsConfig extends AntPathMatcher {

    public static List<String> excludePatterns = new ArrayList();

    public static void addExcludePathPatterns(String... patterns){

        excludePatterns.addAll(Arrays.asList(patterns));
    }
}
