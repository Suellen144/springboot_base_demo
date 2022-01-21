package com.example.demo;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.example.demo.entity.SysUser;
import com.example.demo.entity.SysUserDto;
import com.example.demo.mapper.SysUserMapper;
import com.example.demo.utils.CommonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @Description: demo
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo
 * @Author: Suellen
 * @CreateDate: 2022/1/19 14:13
 */
@SpringBootTest
public class CommonDemo {

    @Resource
    SysUserMapper sysUserMapper;

    @Test
    public void CommonUtilsTest(){
        List<SysUser> sysUserList = sysUserMapper.selectList(null);
        List<SysUserDto> sysUserDtoList = CommonUtils.convertList(sysUserList, SysUserDto.class);
        System.out.println(sysUserDtoList.toString());
    }

    @Test
    public void CollectionUtilsTest(){
        //CollectionUtils.isEmpty()方法对于实例化却没有值的list返回true,Objects.isNull()返回false
        //Objects.isNull()认为list实例化就不为空，CollectionUtils.isEmpty()认为实例化没有值也为空
        List<SysUser> sysUserList = sysUserMapper.selectList(null);
        List<SysUser> sysUserList1 = new ArrayList<>();
        boolean empty = CollectionUtils.isEmpty(sysUserList1);
        boolean aNull = Objects.isNull(sysUserList1);
        //CollectionUtils.isEmpty()方法对于实例化却没有值的map返回true,Objects.isNull()返回false
        //Objects.isNull()认为map实例化就不为空，CollectionUtils.isEmpty()认为实例化没有值也为空
        HashMap<String, String> map = new HashMap<>();
        boolean empty1 = CollectionUtils.isEmpty(map);
        boolean aNull1 = Objects.isNull(map);
        System.out.println("empty:"+empty+",aNull:"+aNull+",empty1:"+empty1+",aNull1:"+aNull1);

        //Objects.isNull()对于实例化的对象判断不为空
        SysUser sysUser = new SysUser();
        boolean aNull2 = Objects.isNull(sysUser);
        System.out.println("aNull2:"+aNull2);

        //查询为空时，接收的对象值为null,没有实例化，就是SysUser zz = null;
        SysUser zz = new LambdaQueryChainWrapper<>(sysUserMapper).eq(SysUser::getPhone, "zz").one();
        boolean aNull3 = Objects.isNull(zz);
        System.out.println("aNull3:"+aNull3+"======zz:"+zz);
    }

    @Test
    public void test1(){
        //只有一行代码的时候，可以省略大括号
        if(false) System.out.println(000);System.out.println(999);
        System.out.println(123);
        System.out.println(456);
        System.out.println(789);
    }
}
