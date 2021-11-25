package com.example.demo.service.serviceImpl;

import com.example.demo.entity.UserTest;
import com.example.demo.mapper.UserTestMapper;
import com.example.demo.service.UserTestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhk
 * @since 2021-11-25
 */
@Service
public class UserTestServiceImpl extends ServiceImpl<UserTestMapper, UserTest> implements UserTestService {

}
