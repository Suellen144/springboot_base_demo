package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.SysUser;
import com.example.demo.mapper.SysUserMapper;
import com.example.demo.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: redis测试
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo
 * @Author: Suellen
 * @CreateDate: 2022/1/18 15:00
 */
@SpringBootTest
public class RedisDemo {

    @Resource
    RedisUtils redisUtils;
    @Resource
    SysUserMapper sysUserMapper;

    @Test
    public void redis1(){
        List<SysUser> sysUserList = sysUserMapper.selectList(null);

        List<SysUser2> list = new ArrayList<>();
        SysUser2 sysUser = new SysUser2();
        list.add(sysUser);
        redisUtils.set( "user:admin", JSON.toJSONString(sysUserList));
        redisUtils.set( "user:admin2", list);
    }
 class SysUser2{
     private String userId;

     public String getUserId() {
         return userId;
     }

     public void setUserId(String userId) {
         this.userId = userId;
     }
 }
    @Test
    public void redis11(){

        Object o = redisUtils.get("user:admin");
        String jsonString = JSON.toJSONString(o);
        List<SysUser> sysUserList = JSON.parseArray(redisUtils.get("user:admin").toString(), SysUser.class);
        System.out.println(sysUserList.toString());
    }

    @Test
    public void redis2(){
        redisUtils.set( "user:root","Suellen");
    }

    @Test
    public void redisGet(){
        Object o = redisUtils.get("user:admin");
        System.out.println(o.toString());
    }

    @Test
    public void redisDelete(){
        redisUtils.del("user:admin");
    }

    @Test
    public void redisHset(){
       redisUtils.hset( "user:Suellen", "user_1", "{\"name\":\"Suellen\",\"age\":134}");
       redisUtils.hset( "user:Suellen", "user_2", "{\"name\":\"Suellen\",\"age\":135}");
    }
}
