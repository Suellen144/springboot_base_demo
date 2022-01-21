package com.example.demo.service.serviceImpl;

import com.example.demo.entity.SpringScheduledCron;
import com.example.demo.mapper.SpringScheduledCronMapper;
import com.example.demo.service.SpringScheduledCronService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定时任务表 服务实现类
 * </p>
 *
 * @author zhk
 * @since 2022-01-07
 */
@Service
public class SpringScheduledCronServiceImpl extends ServiceImpl<SpringScheduledCronMapper, SpringScheduledCron> implements SpringScheduledCronService {

}
