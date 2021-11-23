package com.example.demo.service.serviceImpl;

import com.example.demo.entity.AopDemo;
import com.example.demo.mapper.AopDemoMapper;
import com.example.demo.service.AopDemoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhk
 * @since 2021-11-20
 */
@Service
public class AopDemoServiceImpl extends ServiceImpl<AopDemoMapper, AopDemo> implements AopDemoService {

}
