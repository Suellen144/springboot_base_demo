package com.example.demo.utils;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author du
 */
public class CommonUtils {
    public CommonUtils() {
    }

    public static <T> T convert(Object entity, Class<T> toClass) {
        if (entity == null) {
            return null;
        } else {
            T dto = newInstance(toClass);
            if (Objects.nonNull(dto)) {
                BeanUtils.copyProperties(entity, dto);
            }
            return dto;
        }
    }

    public static <T> T newInstance(Class<T> targetClass) {
        try {
            return targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException var2) {
            var2.printStackTrace();
        }

        return null;
    }

    public static <T, E> List<T> convertList(Collection<E> entitys, Class<T> toClass) {
        List<T> dtos = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(entitys)) {

            for (E entity : entitys) {
                T dto = convert(entity, toClass);
                dtos.add(dto);
            }
        }

        return dtos;
    }
}