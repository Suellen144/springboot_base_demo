package com.example.demo.utils;


import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @Description: 集合数据拷贝工具类
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.utils
 * @Author: Suellen
 * @CreateDate: 2021/12/21 9:17
 */
public class BeanCopyUtils extends BeanUtils {

    /**
     * 集合数据的拷贝
     * @param sources: 数据源类
     * @param target: 目标类::new(eg: UserVo::new)
     * @return
     */
    public static <S,T> List<T> copyListProperties(List<S> sources, Supplier<T> target){
        return copyListProperties(sources,target,null);
    }

    /**
     *带回调函数的集合数据的拷贝（可自定义字段拷贝规则>
     *@param sources:数据源类
     *@param target:目标类::new(eg: UserVo::new)
     *@param callBack:回调函数
     *@return
     */
    public static <S,T> List<T> copyListProperties(List<S> sources,Supplier<T> target,BeanCopyUtilCallBack<S,T> callBack){
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            copyProperties(source,t);
            list.add(t);
            if (callBack != null) {
                //回调
                callBack.callBack(source, t);
            }
        }
        return list;
    }
}
