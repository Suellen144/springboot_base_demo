package com.example.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 校验工具类
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.utils
 * @Author: Suellen
 * @CreateDate: 2021/12/20 9:39
 */
public class RegularCheckUtil {

    /**
     *邮箱验证*/
    public static final String REGEX_EMAIL =
            "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    /**
     *正则表达式:验证身份证*/
    public static final String REGEX_ID_CARD =
            "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|\"+\"(^[1-9]\\\\d{5}\\\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\\\d{3}$)";
    /**
     *正则表达判断手机号*/
    public static final String REGEX_PHONE =
            "^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$";

    private static final Pattern HK_PATTERN = Pattern.compile("^(5|6|8|9)\\d{7}$");

    public static Boolean checkEmail(String email){ return Pattern.matches(REGEX_EMAIL,email);}
    public static Boolean checkIdCard(String idCard){ return Pattern.matches(REGEX_ID_CARD,idCard);}
    public static Boolean checkPhone(String phone){ return Pattern.matches(REGEX_PHONE ,phone);}
    public static Boolean checkPassWord(String passWord){
            String regEx="[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*()——|{}【】‘；：”“'。，、？%+_]{8,16}$";
            //构造一个模式.
            final Pattern p = Pattern.compile(regEx);
            final Pattern pA = Pattern.compile( "^[a-zA-Z]{8,16}$");
            final Pattern pNum = Pattern.compile("^[0-9]{8,16}$");
            //建造一个匹配器
            Matcher m  = p.matcher(passWord);
            Matcher m1 = pA.matcher(passWord);
            Matcher m2 = pNum.matcher(passWord);
            //大小写字母，数字，特殊字符全集匹配（只要字符串匹配其中任何一个或多个都可以)
            String reg="([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]){8,16}$";
            Pattern pAll=Pattern.compile(reg);
            Matcher mAll = pAll.matcher(passWord);
            //进行判断，得到结果
            //因为字符串str如果匹配一个就不可能匹配其他的，具有互异性。还要排除都不匹配的情况，不满足这四项的字符
            if(m.matches()||m1.matches()||m2.matches()){
                //只满足一种情况不符合要求
                return false;
            }else if(mAll.matches()){
                //符合8-16位的密码要求
                return true;
            }else{
                //不符合要求
                return false;
            }
        }
    }
