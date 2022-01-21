package com.example.demo;

import com.example.demo.entity.pojo.UserDemo;
import com.example.demo.enums.AopEnum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    void instanceTest() {
        UserDemo userDemo = UserDemo.getInstance();
        userDemo.setName("张三");
        userDemo.setPhone("123");
        System.out.println(userDemo.toString());

        AopEnum[] values = AopEnum.values();
        AopEnum aopEnum = AopEnum.valueOf("ONE_DEMO");
        System.out.println("=="+values.toString());
        System.out.println("--"+aopEnum.toString());
    }
}
