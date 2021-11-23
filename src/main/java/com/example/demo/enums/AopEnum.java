package com.example.demo.enums;

/**
 * <p>
 *  枚举类
 * </p>
 * @Author zhk
 * @Date 2021/11/20 22:26
 * @Version 1.0
 **/
public enum AopEnum {

    ONE_DEMO("ONE_DEMO","用户", "用户姓名:,%s,年龄:,%s,电话:,%s"),
    TWO_DEMO("TWO_DEMO","部门", "部门名称:,%s,部门地址:,%s"),
    THREE_DEMO("THREE_DEMO","dd", "奥迪");

    /**编码*/
    public String code;
    /**类型*/
    public String type;
    /**内容*/
    public String desc;

    AopEnum(String code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public static String getDesc(String type) {
        AopEnum[] aopEnums = values();
        for (AopEnum aopEnum : aopEnums) {
            if (aopEnum.type.equals(type)) {
                return aopEnum.desc;
            }
        }
        return null;
    }

    public static String getType(String desc) {
        AopEnum[] aopEnums = values();
        for (AopEnum aopEnum : aopEnums) {
            if (aopEnum.desc.equals(desc)) {
                return aopEnum.type;
            }
        }
        return null;
    }
}
