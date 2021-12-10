package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.demo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhk
 * @since 2021-11-19
 */
public interface UserMapper extends BaseMapper<User> {

    User selectUserById(@Param("id") Integer id);

    @Update("update user set name = '张三啊' where id = #{id}")
    Boolean updateUserById(@Param("id") Integer id);

    List<User> selectUserByCondition(@Param("user")User user);

    List<User> selectUserByCondition2(
            @Param("name") String name,
            @Param("phone") String phone,
            @Param("deptName") String deptName);


    List<User> selectUserByCondition3(@Param(Constants.WRAPPER) QueryWrapper<User> queryWrapper);
}
