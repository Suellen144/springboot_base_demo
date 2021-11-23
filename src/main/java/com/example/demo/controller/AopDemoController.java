package com.example.demo.controller;


import com.example.demo.annotation.AopAnnotation;
import com.example.demo.entity.AopDemo;
import com.example.demo.entity.Dept;
import com.example.demo.enums.AopEnum;
import com.example.demo.mapper.AopDemoMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhk
 * @since 2021-11-20
 */
@RestController
@RequestMapping("/aop-demo")
public class AopDemoController {

    @Resource
    AopDemoMapper aopDemoMapper;

    @PostMapping("/addAapDemo")
    @ApiOperation(value = "增加AOP",notes = "增加AOP")
    public int addAapDemo(@RequestBody AopDemo aopDemo){
        aopDemoMapper.insert(aopDemo);
        return aopDemo.getId();
    }

}

