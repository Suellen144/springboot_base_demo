package com.example.demo.annotation;

import com.example.demo.enums.AopEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author zhk
 * @Date 2021/11/20 22:47
 * @Version 1.0
 **/
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface AopAnnotation {

    AopEnum aopEnum();
}
