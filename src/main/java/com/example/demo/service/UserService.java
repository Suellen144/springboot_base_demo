package com.example.demo.service;

import com.example.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhk
 * @since 2021-11-19
 */
public interface UserService extends IService<User> {

    User selectUserById(Integer id);

    Boolean updateUserById(Integer id);
}
