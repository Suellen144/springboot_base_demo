package com.example.demo.controller;

import com.example.demo.common.R;
import com.example.demo.entity.SysUser;
import com.example.demo.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zhk
 * @since 2021-12-21
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Resource
    SysUserService sysUserService;

    /**
     * 用户注册
     * @param sysUser 用户对象
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户注册",notes = "用户注册")
    public R register(@RequestBody SysUser sysUser){
        return sysUserService.register(sysUser);
    }

    /**
     * 用户登录
     * @param sysUser 用户对象
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录",notes = "用户登录")
    public R login(@RequestBody SysUser sysUser){
        return sysUserService.login(sysUser);
    }

    /**
     * 生成六位数短信验证码
     * @param phone 手机号码
     */
    @PostMapping("/getMessageCode")
    @ApiOperation(value = "生成六位数短信验证码",notes = "生成六位数短信验证码")
    public R getMessageCode(@RequestParam("phone") String phone){
        return sysUserService.getMessageCode(phone);
    }

    /**
     * 校验手机号和短信验证码
     * @param phone 手机号码
     * @param messageCode 短信验证码
     */
    @PostMapping("/verifyPhoneAndMessageCode")
    @ApiOperation(value = "校验手机号和短信验证码",notes = "校验手机号和短信验证码")
    public R verifyPhoneAndMessageCode(@RequestParam("phone") String phone,
                                       @RequestParam("messageCode") String messageCode){
        return sysUserService.verifyPhoneAndMessageCode(phone,messageCode);
    }

    /**
     * 重置密码
     * @param phone 手机号码
     * @param newPassword 新密码
     */
    @PostMapping("/resetPassword")
    @ApiOperation(value = "重置密码",notes = "重置密码")
    public R resetPassword(@RequestParam("phone") String phone,
                           @RequestParam("newPassword") String newPassword){
        return sysUserService.resetPassword(phone,newPassword);
    }

    /**
     * 修改密码
     * @param userId 用户id
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     */
    @PostMapping("/updatePassword")
    @ApiOperation(value = "修改密码",notes = "修改密码")
    public R updatePassword(@RequestParam("userId") String userId,
                            @RequestParam("newPassword") String newPassword,
                            @RequestParam("oldPassword") String oldPassword){
        return sysUserService.updatePassword(userId,newPassword,oldPassword);
    }

    /**
     * 修改手机号码
     * @param userId 用户id
     * @param newPhone 新密码
     * @param oldPhone 旧密码
     */
    @PostMapping("/updatePhone")
    @ApiOperation(value = "修改手机号码",notes = "修改手机号码")
    public R updatePhone(   @RequestParam("userId") String userId,
                            @RequestParam("newPhone") String newPhone,
                            @RequestParam("oldPhone") String oldPhone){
        return sysUserService.updatePhone(userId,newPhone,oldPhone);
    }

    /**
     * 查询所有用户
     */
    @GetMapping("/findUserAll")
    @ApiOperation(value = "查询所有用户",notes = "查询所有用户")
    public R findUserAll(){
        return sysUserService.findUserAll();
    }
}

