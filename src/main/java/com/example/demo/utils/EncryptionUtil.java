package com.example.demo.utils;

import java.util.Base64;

/**
 * @Description: 加密工具类
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.utils
 * @Author: Suellen
 * @CreateDate: 2021/12/21 14:21
 */
public class EncryptionUtil {

    public static String encryption(String password){
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
