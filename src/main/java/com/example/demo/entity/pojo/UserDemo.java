package com.example.demo.entity.pojo;

import lombok.Data;

/**
 * @Description: 枚举创建单例
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.entity.pojo
 * @Author: Suellen
 * @CreateDate: 2022/1/5 9:32
 */
@Data
public class UserDemo {

    public String name;
    public String phone;

    /**
     * 私有化构造函数
     */
    private UserDemo(){}

    /**
     * 定义静态枚举类
     */
    static enum SingletonEnum{
        //创建一个枚举对象，该对象天生为单例
        INSTANCE;
        private UserDemo user;
        /**
         * 私有化枚举的构造函数
         */
        private SingletonEnum(){
            user=new UserDemo();
        }
        public UserDemo getInstance(){
            return user;
        }
    }

    /**
     * 对外暴露一个获取User对象的静态方法
     */
    public static UserDemo getInstance(){
        return SingletonEnum.INSTANCE.getInstance();
    }
}
