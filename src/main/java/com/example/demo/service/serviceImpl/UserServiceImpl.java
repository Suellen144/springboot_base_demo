package com.example.demo.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Dept;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhk
 * @since 2021-11-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public User selectUserById(Integer id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public Boolean updateUserById(Integer id) {
        return userMapper.updateUserById(id);
    }

    @Override
    public List<User> selectUserByCondition(User user) {
        return userMapper.selectUserByCondition(user);
    }

    @Override
    public List<User> selectUserByCondition2(@RequestParam("name") String name,
                                             @RequestParam("phone") String phone,
                                             @RequestParam("deptName") String deptName) {
        return userMapper.selectUserByCondition2(name,phone,deptName);
    }

    @Override
    public List<User> selectUserByCondition3(String name, String phone, String deptName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(name)){
            queryWrapper.like("user.name" , name);
        }
        if(StringUtils.isNotBlank(phone)){
            queryWrapper.like("user.phone" , phone);
        }
        if(StringUtils.isNotBlank(deptName)){
            queryWrapper.like("dept.dept_name" , deptName);
        }
        queryWrapper.last("order by user.id desc");

        return userMapper.selectUserByCondition3(queryWrapper);
    }
}
