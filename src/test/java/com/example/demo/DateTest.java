package com.example.demo;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * @Author zhk
 * @Date 2021/11/23 9:39
 * @Version 1.0
 **/

@SpringBootTest
public class DateTest {

    @Resource
    UserMapper userMapper;

    @Test
    public void dateTest() {
        String formatDateTime = DateUtil.formatDateTime(new Date());
        System.out.println(formatDateTime+"====="+new Date());

        System.out.println(DateUtil.format(DateUtil.date(), "yyyyMMdd"));
        System.out.println(DateUtil.format(DateUtil.date(), "HHmmss"));

        System.out.println(DateUtil.date());
        System.out.println(DateTime.now().toString());
    }

    @Test
    public void arrayTest() {
        ArrayList<Integer> objects = new ArrayList<>(10000);
        objects.add(1);
        System.out.println(objects.size());
    }


    @Test
    public void selectMapsPage(){
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        userLambdaQueryWrapper./*select(User::getName).
                like(User::getName , "十").*/
                orderByDesc(User::getId);

        //Page<Map<String , Object>> mapPage = new Page<>(1 , 10 , false);
        Page<User> mapPage = new Page<>(1 ,1000);
        if (mapPage.getSize() > 500) {
            String str = "limit %s offset %s";
            String lastLimit = String.format(str, mapPage.getSize(), (mapPage.getCurrent() - 1) * mapPage.getSize());
            userLambdaQueryWrapper.last(lastLimit);
            mapPage.setSize(-1);
        }
        IPage<User> mapIPage = userMapper.selectPage(mapPage , userLambdaQueryWrapper);
        System.out.println("总页数： "+mapIPage.getPages());
        System.out.println("总记录数： "+mapIPage.getTotal());
        System.out.println("size： "+mapIPage.getSize());
        System.out.println("Current： "+mapIPage.getCurrent());
        mapIPage.getRecords().forEach(System.out::println);
    }

    @Test
    public void timeTest() {
        System.out.println(Instant.now());
        String formatDateTime = DateUtil.formatDateTime(new Date());
    }
}
