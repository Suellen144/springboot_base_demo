package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.Properties;

/**
 * @Description: 启动类
 * @EnableScheduling //开启定时任务功能
 * @EnableCaching // 启用缓存功能
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo
 * @Author: Suellen
 * @CreateDate: 2021/11/18
 */
@MapperScan("com.example.demo.mapper")
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo.**"})
@EnableScheduling
@EnableCaching
public class DemoApplication {

    public static void main(String[] args) throws IOException {
        Properties properties = PropertiesLoaderUtils.loadAllProperties("application.yml");
        String cname = (String)properties.get("cname");
        System.out.println("+++"+cname);
        SpringApplication.run(DemoApplication.class, args);
    }
}
