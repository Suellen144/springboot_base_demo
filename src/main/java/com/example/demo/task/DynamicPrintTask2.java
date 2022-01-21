package com.example.demo.task;

import com.example.demo.entity.SysUser;
import com.example.demo.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 定时任务类2
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.task
 * @Author: Suellen
 * @CreateDate: 2022/1/7 16:52
 */
@Component
@Slf4j
public class DynamicPrintTask2 implements ScheduledOfTask{
    AtomicInteger atomicInteger = new AtomicInteger(1);
    @Resource
    SysUserMapper sysUserMapper;
    @Override
    public void execute() {
        log.info("thread id:{},DynamicPrintTask2 execute times:{}", Thread.currentThread().getId(),atomicInteger.incrementAndGet());
        System.out.println("=============================定时任务2开始了");
        List<SysUser> sysUserList = sysUserMapper.selectList(null);
        System.out.println(sysUserList.toString());
    }
}
