package com.example.demo.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.support.CronExpression;

/**
 * @Description: 定时任务工具类
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.utils
 * @Author: Suellen
 * @CreateDate: 2022/1/7 14:18
 */
public class TaskUtils {

    /**
     * 校验定时任务格式
     *@songwen
     *2018年8月28日
     * @param expressionStr
     * @return
     */
    public static boolean isValidExpressionStr(String expressionStr){
        if(StringUtils.isBlank(expressionStr)){
            return false;
        }
        return CronExpression.isValidExpression(expressionStr);
    }
}
