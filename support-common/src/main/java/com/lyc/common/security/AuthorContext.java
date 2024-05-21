package com.lyc.common.security;

/**
 * @author: liuyucai
 * @Created: 2023/6/12 9:10
 * @Description:
 */
public class AuthorContext {

    private static ThreadLocal<String> author = new ThreadLocal();


    public static void addAuthor(String userId){
        author.set(userId);
    }
    public static String getAuthor(){
        return author.get();
    }
}
