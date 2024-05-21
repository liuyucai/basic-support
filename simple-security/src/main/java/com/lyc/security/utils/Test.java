package com.lyc.security.utils;

/**
 * @author: liuyucai
 * @Created: 2023/7/31 12:00
 * @Description:
 */
public class Test {

    public static void main(String[] args){
        String aaa = "/user/{id}/name/{age}";

        aaa= aaa.replaceAll("\\{.*?\\}","*");

        System.out.println(aaa);
    }
}
