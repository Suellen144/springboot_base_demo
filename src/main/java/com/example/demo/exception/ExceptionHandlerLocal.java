package com.example.demo.exception;

import com.example.demo.common.HttpStatus;
import com.example.demo.common.R;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * @Description: 局部异常处理
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.exception
 * @Author: Suellen
 * @CreateDate: 2022/1/6 10:15
 */

@Slf4j
@Order(1)
@RestControllerAdvice
public class ExceptionHandlerLocal {

    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     *
     * @param e 错误对象
     * @return R
     * @date 2019/10/28 20:40
     */
    @ExceptionHandler(BindException.class)
    public R bindExceptionHandler(BindException e) {
        String message = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return R.error(HttpStatus.ERROR,"入参校验失败", message);
    }

    /**
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
     *
     * @param e 错误对象
     * @return R
     * @date 2019/10/28 20:40
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public R constraintViolationExceptionHandler(ConstraintViolationException e) {
        String message =
                e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
        return R.error(HttpStatus.ERROR,"入参校验失败", message);
    }

    /**
     * 处理请求参数格式错误 @RequestParam参数错误
     *
     * @param e 错误对象
     * @return R
     * @date 2019/10/28 20:40
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R missingServletRequestParameterException(MissingServletRequestParameterException e) {
        return R.error(HttpStatus.ERROR,"参数错误",e.getMessage());
    }
    /**
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
     *
     * @param e 错误对象
     * @return R
     * @date 2019/10/28 20:40
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
        return R.error(HttpStatus.ERROR,"入参校验失败", message);
    }

    /**
     * 自定义错误异常处理
     *
     * @param response 返回处理类
     * @param ex       错误对象
     * @return R
     * @date 2020/7/22 9:17
     */
    @ExceptionHandler(CustomExceptionHandler.class)
    public R msAppExceptionHandler(HttpServletResponse response, CustomExceptionHandler ex) {
        ex.printStackTrace();
        return R.error(ex.getCode(),ex.getMessage());
    }

    /**
     * spring入参jackson转换异常捕获
     *
     * @param ex       错误对象
     * @return R
     * @date 2020/7/22 9:17
     */
    @ExceptionHandler(MismatchedInputException.class)
    public R mismatchedInputException(HttpServletResponse response, CustomExceptionHandler ex) {
        ex.printStackTrace();
        return R.error(HttpStatus.ERROR,ex.getMessage());
    }

    /**
     * by zero异常
     *
     * @param ex       错误对象
     * @return R
     * @date 2020/7/22 9:17
     */
    @ExceptionHandler(value = ArithmeticException.class)
    public R arithmeticException(ArithmeticException ex) {
        ex.printStackTrace();
        return R.error(HttpStatus.ERROR,ex.getMessage());
    }
}
