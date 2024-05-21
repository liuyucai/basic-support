package com.lyc.auth.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

import java.security.KeyPair;
import java.util.Base64;
import java.util.UUID;

/**
 * @author: liuyucai
 * @Created: 2023/6/3 15:23
 * @Description:
 */
public class Test {

    public static void main(String args[]){
        KeyPair keyPair = SecureUtil.generateKeyPair("RSA");


//        String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
//        String privateKey= new String(Base64.encodeBase64((keyPair.getPrivate().getEncoded())));

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCtaH3HRyNNLQCcXT7f1KYEjssDI+usiYkdQhQGMfL6DoWmjITYtTjvrfyxyyDjQDuApTYolpIHfB3IRtQ4eo7UGdbZafi5ZTEfXcr1v6u9EB2xrGYUKEf3tkJoys/nw/BOPHGGwtx43tHED6cNUK8VtufUprnk+oK46giWt9YSYQIDAQAB";
        String privateKey= "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAK1ofcdHI00tAJxdPt/UpgSOywMj66yJiR1CFAYx8voOhaaMhNi1OO+t/LHLIONAO4ClNiiWkgd8HchG1Dh6jtQZ1tlp+LllMR9dyvW/q70QHbGsZhQoR/e2QmjKz+fD8E48cYbC3Hje0cQPpw1QrxW259SmueT6grjqCJa31hJhAgMBAAECgYAEdVxOhFi3AkIywv772EbQdS9pDEJh9ENPOkSziBM8NKVhC9najIZDO1D07UkSLrOY8iZ7Uqu8f4dwf8mzDgugA3oBV1YeBdhWjv9/bGNAe/85TDdJ8IBjWPJwpfwBztzdENNTDnuF5Y1NSXyjCB1OSbrdg0JejshO50EkVeYfpQJBAPRQleb7RaSJ8WkT00okhbFf1xxGCgUWyR9fo+qFR07NhUnk3cmgbHY7T4Cf42OByc0VlcXG9YEj3zBxyZecJLsCQQC1s7fWVE0ENN4kaZY/CPufz7yQ8pba+echx/BqS5HkZcvhoEbbXlOm2S0vLCfWVhqu2YBXUcRNoqiRBGd6tMGTAkB+udYni6qiIFMyV/A/Axa93KKtrb7AyQLl3SwfF6bXP0+l9qr8TNQ1t6oTY9wE32uXj1yyy/LIW50Hed5BX+LFAkAe1fsS4oeXeSlh4JFg7TNWsbh8mM7FJrf04auJPn3p1uS8y6h6vg1QOXcTlFjGZSJuHfdL2lKIIlydD7RH/LkFAkAyguC39ibUqbJq0Uoahzhgvcd3y9AAOlGWQi2an6gD9NUGjMAJFuSAGN3frHpXxuo2VVC/jMCGvzPrtXe5WV45";

//        System.out.println(publicKey);
//
//        System.out.println(privateKey);
//
//        String aaa = encrypt("123456",publicKey);

        String cc = "ZZOOJPr6ahNcLVndWvYSdKhzsqzgOFArKFIo21nC6l4lTuKzO/H73IARI0Uc/0PfKIcVLtOLsDOm/pJGHf/CXaDnyNoDleC8Qy6k9o9q1lv18CKuzCYKI6AVOsbL77lTyrqNqWZI15ktEuFCoobqxoy0DHiUbKt61rR1s6gpa30=";


//        RSA rsa = new RSA(privateKey, null);
//        String bbb = rsa.decryptStr(cc, KeyType.PrivateKey);
////        System.out.println(aaa);
//
////        String bbb = decrypt(cc,privateKey);
//
//        System.out.println(bbb);
//
//        String uuid = UUID.randomUUID().toString();
//        String encry1 = SecureUtil.md5("123456"+uuid);
//
//        System.out.println(uuid);
//        System.out.println(encry1);

//        RSA rsa = new RSA(privateKey, publicKey);
//
//        //token：用户名+随机数  加密
//        String random =  RandomUtil.randomString(10);
//
//        System.out.println(random);
//        String token = Base64.getEncoder().encodeToString(rsa.encrypt(random, KeyType.PublicKey));
//
//        System.out.println(token);
//
//        String decrypt = rsa.decryptStr(token, KeyType.PrivateKey);
//
//        System.out.println(decrypt);


        String token = "adminlyc1234567";
        System.out.println(token.substring(0,token.length()-10));

        System.out.println(token.substring(token.length()-10));


    }

    /**
     * 公钥加密
     *
     * @param content   要加密的内容
     * @param publicKey 公钥
     */
    public static String encrypt(String content, String publicKey) {
        try {
            RSA rsa = new RSA(null, publicKey);
            return rsa.encryptBase64(content, KeyType.PublicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥解密
     *
     * @param content 要解密的内容
     */
    public static String decrypt(String content,String privateKey) {
        try {
            RSA rsa = new RSA(privateKey, null);
            return rsa.decryptStr(content, KeyType.PrivateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
