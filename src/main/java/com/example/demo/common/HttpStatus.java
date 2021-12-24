package com.example.demo.common;

/**
 * @Description: 状态统一管理
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.common
 * @Author: Suellen
 * @CreateDate: 2021/12/20 14:24
 */
public class HttpStatus {

    /**
     * 操作成功
     * */
    public static final int SUCCESS = 200 ;
    /**
     * 未授权
     */
    public static final int UNAUTHORIZED = 401;
     /**
      * 访问受限，授权过期
      * */
    public static final int FORBIDDEN = 403;
    /**
     * 系统内部错误
     * */
    public static final int ERROR = 500;

}
