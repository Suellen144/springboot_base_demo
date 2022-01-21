package com.example.demo.exception;

import com.example.demo.common.HttpStatus;
import com.example.demo.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 全局统一异常处理
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.exception
 * @Author: Suellen
 * @CreateDate: 2022/1/6 10:21
 */

@Slf4j
@Order(2)
@RestControllerAdvice
public class ExceptionHandlerGlobal {

    @ExceptionHandler(Exception.class)
    public R exception(HttpServletRequest request, Exception exception) {
        log.error(request.getRequestURL() + request.getContextPath() + exception.getMessage(), exception);
        // 异常信息
        return R.error(HttpStatus.ERROR,"系统异常，请重试");
    }
}
