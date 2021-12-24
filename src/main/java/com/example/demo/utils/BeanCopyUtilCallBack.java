package com.example.demo.utils;

/**
 * @Description: 函数式接口
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.utils
 * @Author: Suellen
 * @CreateDate: 2021/12/21 9:14
 */

@FunctionalInterface
public interface BeanCopyUtilCallBack <S,T>{

   /**
    * 定义默认回调方法*
    * @param t
    * @param s
    */
    void callBack(S t,T s);
}
