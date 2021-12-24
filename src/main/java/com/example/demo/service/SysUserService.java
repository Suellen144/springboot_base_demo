package com.example.demo.service;

import com.example.demo.common.R;
import com.example.demo.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zhk
 * @since 2021-12-21
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 用户注册
     * @param sysUser 用户对象
     * @return R
     */
    R register(SysUser sysUser);

    /**
     * 用户登录
     * @param sysUser 用户对象
     * @return R
     */
    R login(SysUser sysUser);

    /**
     * 生成六位短信验证码
     * @param phone 手机号码
     * @return R
     */
    R getMessageCode(String phone);

    /**
     * 校验手机号和短信验证码
     * @param phone 手机号码
     * @param messageCode 短信验证码
     * @return R
     */
    R verifyPhoneAndMessageCode(String phone, String messageCode);

    /**
     * 重置密码
     * @param phone 手机号码
     * @param newPassword 新密码
     * @return R
     */
    R resetPassword(String phone, String newPassword);

    /**
     * 修改密码
     * @param userId 用户id
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     * @return R
     */
    R updatePassword(String userId, String newPassword, String oldPassword);

    /**
     * 修改手机号码
     * @param userId 用户id
     * @param newPhone 新密码
     * @param oldPhone 旧密码
     * @return R
     */
    R updatePhone(String userId, String newPhone, String oldPhone);

    /**
     * 查询所有用户
     * @return
     */
    R findUserAll();
}
