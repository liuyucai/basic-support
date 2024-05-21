package com.lyc.simple.utils;

/**
 * @author: liuyucai
 * @Created: 2023/3/11 11:05
 * @Description:
 */
public class SQLTranslator {

    private static String DATABASE;

    public SQLTranslator() {
    }

    public static void setDatabase(String database) {
        DATABASE = isBlank(database) ? "MYSQL" : database;
    }

    public static String paging() {
        String sqlFragment = "";
        switch(DATABASE) {
            case "MYSQL":
                sqlFragment = " limit :offset,:size ";
                break;
            case "DM":
                sqlFragment = " limit :offset,:size ";
                break;
            case "POSTGRESQL":
                sqlFragment = " limit :size offset :offset ";
        }

        return sqlFragment;
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }
}
