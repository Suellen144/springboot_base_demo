package com.example.demo.controller;


import com.example.demo.annotation.AopAnnotation;
import com.example.demo.entity.User;
import com.example.demo.entity.UserTest;
import com.example.demo.enums.AopEnum;
import com.example.demo.mapper.UserTestMapper;
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
 * @since 2021-11-25
 */
@RestController
@RequestMapping("/userTest")
public class UserTestController {

    @Resource
    UserTestMapper userTestMapper;

    @PostMapping("/addUserTest")
    @ApiOperation(value = "增加测试用户",notes = "增加测试用户")
    @AopAnnotation(aopEnum = AopEnum.THREE_DEMO)
    public String addUserTest(@RequestBody UserTest userTest){
        userTestMapper.insert(userTest);
        return userTest.getId();
    }
}

