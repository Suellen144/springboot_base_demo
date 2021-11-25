package com.example.demo;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.User;
import com.example.demo.entity.UserTest;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserTestMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhk
 * @Date 2021/11/23 9:39
 * @Version 1.0
 **/

@SpringBootTest
@Slf4j
public class DateTest {

    @Resource
    UserMapper userMapper;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    UserTestMapper userTestMapper;

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
    public void resultTest() {
        String url = "http://localhost:9000/user/selectUserById?id={id}";
        HashMap<String,Object> map = new HashMap<>();
        map.put("id",30);
        String forObject = restTemplate.getForObject(url,String.class,map);
        JSONObject jsonObject = JSONObject.parseObject(forObject);
        Object data = jsonObject.get("data");
        User user = JSON.parseObject(jsonObject.get("data").toString(), User.class);
        System.out.println(forObject);
        System.out.println("==="+user.toString());
    }

    @Test
    public void userTest2() {
        UserTest userTest = new UserTest();
        userTest.setName("测试");
        userTestMapper.insert(userTest);
    }
}
