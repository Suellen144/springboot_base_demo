package com.example.demo.utils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhk
 * @Date 2021/11/21 20:04
 * @Version 1.0
 *
 * List<Object[]>转换成一个List<T>
 **/

public class ObjectToBeanUtils {
    /**
     * 把List<Object[]>转换成List<T>
     */
    public static <T> List<T> objectToBean(List<Object[]> objList, Class<T> clz) throws Exception{
        if (objList==null || objList.size()==0) {
            return null;
        }

        Class<?>[] cz = null;
        Constructor<?>[] cons = clz.getConstructors();
        for (Constructor<?> ct : cons) {
            Class<?>[] clazz = ct.getParameterTypes();
            if (objList.get(0).length == clazz.length) {
                cz = clazz;
                break;
            }
        }

        List<T> list = new ArrayList<T>();
        for (Object[] obj : objList) {
            Constructor<T> cr = clz.getConstructor(cz);
            list.add(cr.newInstance(obj));
        }
        return list;
    }
}
