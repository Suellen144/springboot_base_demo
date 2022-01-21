package com.example.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 定时任务类
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.task
 * @Author: Suellen
 * @CreateDate: 2022/1/7 11:18
 */
@Component
@Slf4j
public class DynamicPrintTask  implements ScheduledOfTask{
    AtomicInteger atomicInteger = new AtomicInteger(1);
    @Override
    public void execute() {
        log.info("thread id:{},DynamicPrintTask execute times:{}", Thread.currentThread().getId(),atomicInteger.incrementAndGet());
        System.out.println("=============================定时任务开始了");
    }
}
