package com.example.demo.aop;

import com.example.demo.annotation.AopAnnotation;
import com.example.demo.entity.AopDemo;
import com.example.demo.entity.Dept;
import com.example.demo.entity.User;
import com.example.demo.enums.AopEnum;
import com.example.demo.mapper.AopDemoMapper;
import com.example.demo.service.AopDemoService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * @Author zhk
 * @Date 2021/11/20 22:53
 * @Version 1.0
 **/
@Aspect
@Order(0)
@Component
public class AopTest {

    @Resource
    AopDemoMapper aopDemoMapper;

    @Pointcut("@annotation(com.example.demo.annotation.AopAnnotation)")
    public void aopPoint() {
    }

    @AfterReturning(value = "aopPoint()",returning= "id")
    public void aopPoints(JoinPoint point ,Integer id) {
        MethodSignature signature = (MethodSignature)point.getSignature();
        AopAnnotation aopAnnotation = signature.getMethod().getAnnotation(AopAnnotation.class);
        Object arg = point.getArgs()[0];
        AopEnum aopEnum = aopAnnotation.aopEnum();
        switch (aopEnum){
            case ONE_DEMO:
                User user =  (User) arg;
                String desc = String.format(aopEnum.desc, user.getName(), user.getAge(), user.getPhone());

                CompletableFuture.runAsync(() -> {
                    addAopDemo(aopEnum.type,desc);
                });
                System.out.println("id:"+id);
                break;
            case TWO_DEMO:
                Dept dept =  (Dept) arg;
                String format = String.format(aopEnum.desc, dept.getDeptName(),dept.getDeptAddress());

                CompletableFuture.runAsync(() -> {
                    addAopDemo(aopEnum.type,format);
                });
                System.out.println("id:"+id);
                break;
            case THREE_DEMO:
                break;
            default:
                break;
        }
    }

    public void addAopDemo(String type,String desc){
        AopDemo aopDemo = new AopDemo();
        aopDemo.setType(type);
        aopDemo.setContent(desc);
        aopDemoMapper.insert(aopDemo);

        System.out.println("id:"+aopDemo.getId());
    }
}
