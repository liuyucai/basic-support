package com.lyc.minio.utils;

/**
 * @author: liuyucai
 * @Created: 2023/3/25 16:04
 * @Description:
 */
public class StringUtils {
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }
}
