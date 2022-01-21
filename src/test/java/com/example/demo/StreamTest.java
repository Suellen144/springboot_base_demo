package com.example.demo;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.example.demo.entity.User;
import com.example.demo.entity.UserTest;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserTestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: Stream测试
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo
 * @Author: zhk
 * @CreateDate: 2021/12/5 16:18
 */

@SpringBootTest
public class StreamTest {

    @Resource
    UserMapper userMapper;
    @Resource
    UserTestMapper userTestMapper;

    @Test
    public void streamTest(){
        List<User> users = userMapper.selectList(null);
        System.out.println("-----"+users.toString());
        users.forEach(user -> {
            user.setName("123");
        });
        System.out.println("++++++"+users.toString());
    }

    @Test
    public void streamTest2(){
        List<User> users = userMapper.selectList(null);
        //map和peek最好结合forEach使用
        //forEach遍历可以返回值
        //map和peek的toList可以改变原来list的值
        System.out.println("-----"+users.toString());
        users.stream().peek(user -> {
            user.setName("456");
        }).forEach(System.out::println);
        System.out.println("++++++"+users.toString());
        //System.out.println("======="+collect.toString());
    }


    @Test
    public void streamTest3(){
        User user = new User();
        System.out.println(user.getName());
        System.out.println(user.getName()+user.getAge());
        System.out.println(user);
        System.out.println(new User());
    }


    @Test
    public void Test4(){
        List<UserTest> userTests = userTestMapper.selectList(null);
        System.out.println(userTests);
        System.out.println(userTests.get(0));
    }

    @Test
    public void Test5(){
        List<User> list = new LambdaQueryChainWrapper<>(userMapper).eq(User::getPhone,"123456").list();
        System.out.println("+++"+list.toString());

        User one = new LambdaQueryChainWrapper<>(userMapper).eq(User::getId,30).one();
        System.out.println("---"+one);

        User id = new QueryChainWrapper<>(userMapper).eq("id", 30).one();
        System.out.println("id"+id);

        List<User> list1 = new LambdaQueryChainWrapper<>(userMapper).select(User::getName).eq(User::getPhone, "123456").list();
        System.out.println("==="+list1.toString());

        String name = new LambdaQueryChainWrapper<>(userMapper).select(User::getName).eq(User::getPhone, "123456").list()+"";
        System.out.println("***"+name);
    }

    @Test
    public void Test6(){
        long aa = 022333;
        System.out.println(00);
        System.out.println(aa++);
        System.out.println(Integer.parseInt("022333"));
        int i = Integer.parseInt("022333");
        i++;
        System.out.println(i);
    }
}
