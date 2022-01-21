package com.example.demo.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.SpringScheduledCron;
import com.example.demo.mapper.SpringScheduledCronMapper;
import com.example.demo.utils.SpringUtils;

/**
 * @Description: 定时任务
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.task
 * @Author: Suellen
 * @CreateDate: 2022/1/7 10:54
 */
public interface ScheduledOfTask extends Runnable {

    /**
     * 任务是禁用状态
     */
    final String DEACTIVATE = "2";

    /**
     * 定时任务方法
     */
    void execute();

    /**
     * 实现控制定时任务启用或禁用的功能
     */
    @Override
    default  void run() {
        SpringScheduledCronMapper repository = SpringUtils.getBean(SpringScheduledCronMapper.class);
        SpringScheduledCron scheduledCron = repository.selectOne(new QueryWrapper<SpringScheduledCron>()
                .eq("cron_key",this.getClass().getName()));
        if (DEACTIVATE.equals(scheduledCron.getStatus()+"")) {
            // 任务是禁用状态
            return;
        }
        execute();
    }
}
