package com.example.demo.exception;

import com.example.demo.common.HttpStatus;

/**
 * @Description: 自定义异常处理
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.exception
 * @Author: Suellen
 * @CreateDate: 2022/1/6 10:25
 */

public class CustomExceptionHandler extends RuntimeException{
    protected Integer code;

    public CustomExceptionHandler(Integer code, String message) {
        this(code, message, null);
    }

    public CustomExceptionHandler(String message) {
        this(null, message, null);
    }

    public CustomExceptionHandler(String message, Throwable cause) {
        this(null, message, cause);
    }

    public CustomExceptionHandler(Throwable cause) {
        this(null, null, cause);
    }

    public CustomExceptionHandler(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    /**
     * @param expression   条件成立抛出异常
     * @param errorMessage 异常信息
     * @return void
     **/
    public static void checkCondition(boolean expression, String errorMessage) {
        if (expression) {
            throw new CustomExceptionHandler(errorMessage);
        }
    }

    /**
     * @param expression   条件成立抛出异常
     * @param errorCode    异常码
     * @param errorMessage 异常信息
     * @return void
     **/
    public static void checkCondition(boolean expression, Integer errorCode, String errorMessage) {
        if (expression) {
            throw new CustomExceptionHandler(errorCode, errorMessage);
        }
    }

    /**
     * 判断参数异常，失败抛异常
     *
     * @param expression   条件成立抛出异常
     * @param errorMessage 异常信息
     * @return void
     **/
    public static void checkParamCondition(boolean expression, String errorMessage) {
        if (expression) {
            throw new CustomExceptionHandler(HttpStatus.ERROR, errorMessage);
        }
    }

    public Integer getCode() {
        return code;
    }
}
