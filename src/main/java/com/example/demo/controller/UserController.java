package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.annotation.AopAnnotation;
import com.example.demo.common.CommonResult;
import com.example.demo.entity.User;
import com.example.demo.enums.AopEnum;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.utils.RedisUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhk
 * @since 2021-11-19
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserMapper userMapper;
    @Resource
    RedisUtils redisUtils;
    @Resource
    UserService userService;

    @GetMapping("/selectUserList")
    @ApiOperation(value = "测试查询所有",notes = "测试查询所有")
    public List<User> selectUserList(){
        List<User> userList = new ArrayList<>();
        boolean hasKey = redisUtils.hasKey("userList");
        if(hasKey){
            //获取缓存
           /* List<Object> objects= redisUtils.lGet("userList1", 0, -1);
            String jsonString = JSON.toJSONString(objects);
            List<User> userList1 = JSON.parseArray(jsonString, User.class);
            userList1.forEach(System.out::println);*/
            System.out.println("-------------------");
            String toString = redisUtils.get("userList").toString();
            List<User> users = JSON.parseArray(toString, User.class);
            List<User> collect = users.stream().sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());
            userList.addAll(collect);
        }else{
            //从数据库中获取信息
            userList = userMapper.selectList(new QueryWrapper<User>().orderByDesc("id"));
            //数据插入缓存（lSet中的参数含义：key值，user对象，缓存存在时间10（long类型），时间单位）
            String jsonString = JSON.toJSONString(userList);
            /*redisUtils.lSet("userList1",userList);*/
            redisUtils.set("userList",jsonString);
        }
        return userList;

    }

    @PostMapping("/addUser")
    @ApiOperation(value = "增加用户",notes = "增加用户")
    @AopAnnotation(aopEnum = AopEnum.ONE_DEMO)
    public int addUser(@RequestBody User user){
       userMapper.insert(user);
       return user.getId();
    }

    @GetMapping("/selectUserById")
    @ApiOperation(value = "查询",notes = "查询")
    public CommonResult<User> selectUserById(@RequestParam Integer id){
        System.out.println(userService.selectUserById(id));
        return new CommonResult<>(200,"测试",userService.selectUserById(id));
    }

    @PostMapping("/updateUserById")
    @ApiOperation(value = "修改",notes = "修改")
    public Boolean updateUserById(@RequestParam Integer id){
        System.out.println(userService.updateUserById(id));
        return userService.updateUserById(id);
    }

    @GetMapping("/selectUserByCondition")
    @ApiOperation(value = "条件查询所有",notes = "条件查询所有")
    public CommonResult<List<User>> selectUserByCondition(@RequestBody User user){
        List<User> user1 = userService.selectUserByCondition(user);
        return new CommonResult<>(200,"测试",user1);
    }

    @GetMapping("/selectUserByCondition2")
    @ApiOperation(value = "条件查询所有2",notes = "条件查询所有2")
    public CommonResult<List<User>> selectUserByCondition2(
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("deptName") String deptName){
        List<User> user = userService.selectUserByCondition2(name,phone,deptName);
        return new CommonResult<>(200,"测试",user);
    }

    @GetMapping("/selectUserByCondition3")
    @ApiOperation(value = "条件查询所有3",notes = "条件查询所有3")
    public CommonResult<List<User>> selectUserByCondition3(
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("deptName") String deptName){
        List<User> user = userService.selectUserByCondition3(name,phone,deptName);
        return new CommonResult<>(200,"测试",user);
    }


    @GetMapping("/selectUserByCondition4")
    @ApiOperation(value = "条件查询所有4",notes = "条件查询所有4")
    public CommonResult<List<User>> selectUserByCondition4(@RequestParam Map<String, Object> map){
        List<User> user = userService.selectUserByCondition3
                (map.get("name").toString(),map.get("phone").toString(),map.get("deptName").toString());
        return new CommonResult<>(200,"测试",user);
    }

    @GetMapping("/selectUserByCondition5/{name}/{phone}/{deptName}")
    @ApiOperation(value = "条件查询所有5",notes = "条件查询所有5")
    public CommonResult<List<User>> selectUserByCondition5
            (@PathVariable String name, @PathVariable String phone, @PathVariable String deptName){
        List<User> user = userService.selectUserByCondition3
                (name,phone,deptName);

        return new CommonResult<>(200,"测试",user);
    }
}

