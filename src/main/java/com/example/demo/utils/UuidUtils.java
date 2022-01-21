package com.example.demo.utils;

import java.util.UUID;

/**
 * @Description: UUID生成工具类
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.utils
 * @Author: Suellen
 * @CreateDate: 2021/12/21 9:08
 */
public class UuidUtils {

    public static String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
