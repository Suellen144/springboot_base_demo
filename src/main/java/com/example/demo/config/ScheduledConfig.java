package com.example.demo.config;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.HttpStatus;
import com.example.demo.entity.SpringScheduledCron;
import com.example.demo.exception.CustomExceptionHandler;
import com.example.demo.mapper.SpringScheduledCronMapper;
import com.example.demo.task.ScheduledOfTask;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @Description: 定时任务配置类
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.config
 * @Author: Suellen
 * @CreateDate: 2022/1/7 10:50
 */
@Configuration
public class ScheduledConfig implements SchedulingConfigurer {

    @Resource
    private ApplicationContext context;
    @Resource
    private SpringScheduledCronMapper cronRepository;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        for (SpringScheduledCron springScheduledCron : cronRepository.selectList(null)) {
            Class<?> clazz;
            Object task;
            try {
                clazz = Class.forName(springScheduledCron.getCronKey());
                task = context.getBean(clazz);
            } catch (ClassNotFoundException e) {
                throw new CustomExceptionHandler(HttpStatus.ERROR,"spring_scheduled_cron表数据" + springScheduledCron.getCronKey() + "有误", e);
            } catch (BeansException e) {
                throw new CustomExceptionHandler(HttpStatus.ERROR,springScheduledCron.getCronKey() + "未纳入到spring管理", e);
            }
            Assert.isAssignable(ScheduledOfTask.class, task.getClass(), "定时任务类必须实现ScheduledOfTask接口");
            // 可以通过改变数据库数据进而实现动态改变执行周期
            taskRegistrar.addTriggerTask(((Runnable) task),
                    triggerContext -> {
                        QueryWrapper<SpringScheduledCron> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("cron_key",springScheduledCron.getCronKey());
                        String cronExpression = cronRepository.selectOne(queryWrapper).getCronExpression();
                        return new CronTrigger(cronExpression).nextExecutionTime(triggerContext);
                    }
            );
        }
    }

    @Bean
    public Executor taskExecutor() {
        return new ScheduledThreadPoolExecutor(10);
        //return Executors.newScheduledThreadPool(10);
    }
}
