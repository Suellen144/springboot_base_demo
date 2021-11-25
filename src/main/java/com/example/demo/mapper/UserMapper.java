package com.example.demo.mapper;

import com.example.demo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

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
}
