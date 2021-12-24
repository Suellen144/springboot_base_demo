package com.example.demo.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @Description: 缓存单例
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.utils
 * @Author: Suellen
 * @CreateDate: 2021/12/20 11:36
 */
public class CacheSingleton {
    private static Cache<String,String> cache = CacheBuilder.newBuilder()
            .maximumSize(500)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    private static CacheSingleton instance;

    //构造方法私有化..防止实例化
    private CacheSingleton(){}
    //单例模式构建对象
    public static CacheSingleton getInstance(){
        if (instance == null) {
            instance = new CacheSingleton();}
        return instance;
    }
    public void putValue(String key,String value){ cache.put(key ,value);}
    public String getValue(String key) { return cache.getIfPresent(key);}
}
