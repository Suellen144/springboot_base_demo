package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.example.demo.entity.SysUser;
import com.example.demo.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo
 * @Author: Suellen
 * @CreateDate: 2022/1/19 14:44
 */
@SpringBootTest
public class MybatisPlusDemo {

    @Resource
    SysUserMapper sysUserMapper;

    @Test
    public void Sort1(){
        List<SysUser> sysUserList = sysUserMapper.selectList(null);
        System.out.println(sysUserList.toString());

        List<SysUser> sysUserList1 = sysUserMapper.selectList(new QueryWrapper<SysUser>().orderByAsc("create_time"));
        System.out.println(sysUserList1.toString());

        //LambdaQueryChainWrapper和LambdaQueryWrapper的区别是LambdaQueryChainWrapper可以直接new查询返回结果
        SysUser create_time = new LambdaQueryChainWrapper<>(sysUserMapper).
                eq(SysUser::getUserId,"96b86f2a0fca5cee78a833eb83a541de").
                orderByAsc(SysUser::getCreateTime).one();
        SysUser create_time1 = new QueryChainWrapper<>(sysUserMapper).
                eq("user_id","96b86f2a0fca5cee78a833eb83a541de").
                orderByAsc("create_time").one();
        SysUser sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>().
                eq("user_id","96b86f2a0fca5cee78a833eb83a541de").
                orderByAsc("create_time"));
        SysUser sysUser2 = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().
                eq(SysUser::getUserId,"96b86f2a0fca5cee78a833eb83a541de").
                orderByAsc(SysUser::getCreateTime));

        System.out.println("==="+create_time);
        System.out.println("---"+create_time1);
        System.out.println("+++"+sysUser);
        System.out.println("***"+sysUser2);

    }
}
