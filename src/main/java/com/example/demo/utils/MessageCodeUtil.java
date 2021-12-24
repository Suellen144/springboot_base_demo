package com.example.demo.utils;

import java.util.Random;

/**
 * @Description: 生成随机六位数
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.utils
 * @Author: Suellen
 * @CreateDate: 2021/12/21 15:34
 */
public class MessageCodeUtil {

    /**
     * 随机生成六位数短信
     * @return
     */
    public static String getMessageAuthCode(){
        StringBuffer flag = new StringBuffer();
        String sources = "0123456789";
        Random rand = new Random();
        for (int j = 0; j<6; j++){
            flag.append(sources.charAt(rand.nextInt( 9)) +"");
        }
        return flag.toString();
    }
}
