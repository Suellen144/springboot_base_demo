package com.example.demo.service;

import com.example.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    List<User> selectUserByCondition(User user);

    List<User> selectUserByCondition2( @RequestParam("name") String name,
                                       @RequestParam("phone") String phone,
                                       @RequestParam("deptName") String deptName);

    List<User> selectUserByCondition3(String name, String phone, String deptName);
}
