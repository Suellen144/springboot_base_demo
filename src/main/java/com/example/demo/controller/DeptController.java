package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.annotation.AopAnnotation;
import com.example.demo.entity.Dept;
import com.example.demo.enums.AopEnum;
import com.example.demo.mapper.DeptMapper;
import com.example.demo.utils.JedisUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhk
 * @since 2021-11-20
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Resource
    DeptMapper deptMapper;
    @Resource
    JedisUtils jedisUtils;

    @GetMapping("/selectDeptList")
    @ApiOperation(value = "测试查询所有",notes = "测试查询所有")
    public List<Dept> selectDeptList(){
        List<Dept> deptList = new ArrayList<>();
        boolean hasKey = jedisUtils.exists("deptList");
        if(hasKey){
            //获取缓存
            System.out.println("-------------------");
            String toString = jedisUtils.get("deptList");
            List<Dept> depts = JSON.parseArray(toString, Dept.class);
            List<Dept> collect = depts.stream().sorted(Comparator.comparing(Dept::getId)).collect(Collectors.toList());
            deptList.addAll(collect);
            Long del = jedisUtils.del("aa", "bb");
            System.out.println(del);
        }else{
            //从数据库中获取信息
            deptList = deptMapper.selectList(new QueryWrapper<Dept>()
                    .orderByDesc("id")).stream().
                    sorted(Comparator.comparing(Dept::getId)).collect(Collectors.toList());
            //数据插入缓存（lSet中的参数含义：key值，user对象，缓存存在时间10（long类型），时间单位）
            String jsonString = JSON.toJSONString(deptList);
            jedisUtils.set("deptList",jsonString);
        }
        return deptList;
    }

    @PostMapping("/addDept")
    @ApiOperation(value = "增加部门",notes = "增加部门")
    @AopAnnotation(aopEnum = AopEnum.TWO_DEMO)
    public int addDept(@RequestBody Dept dept){
        deptMapper.insert(dept);
        return dept.getId();
    }
}

