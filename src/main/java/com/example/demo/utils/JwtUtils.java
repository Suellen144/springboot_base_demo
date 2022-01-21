package com.example.demo.utils;

import com.example.demo.entity.SysUser;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;

/**
 * @Description: JWT工具类
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.utils
 * @Author: Suellen
 * @CreateDate: 2021/12/20 15:00
 */
public class JwtUtils {

    /**
     * 签名使用的秘钥
     */
    final static String SECRET_KEY = "SuellenAndZhouhuakun144AlonginfoIPhone13AandMSIAndMeiandvulgar";

    public static String generateJwt(SysUser sysUser) {

        //如果账号密码正确jwt生成token
        JwtBuilder builder = Jwts.builder();
        HashMap<String, Object> map = new HashMap<>(1);
        //主题，就是token中携带的数据
        //设置token的生成时间
        //设置用户id为token id
        //map中可以存放用户的角色权限信息//设置过期时间1天
        //设置加密方式和加密key
        map.put("userId", sysUser.getUserId());
        String token = builder
                //sub(Subject)：代表这个JWT的主体，这个是一个json格式的字符串
                .setSubject(sysUser.getAccount())
                //iat: jwt的签发时间
                //setIssuedAt(new Date())
                //设置jti(JWT ID)：是JWT的唯一标识，主要用来作为一次性token
                //setId(customer.getCustomerId())
                .setClaims(map)
                //设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compressWith(CompressionCodecs.GZIP)
                //就开始压缩为xxxx.xxxx.xxxx这样的jwt，然后接收方解压
                .compact();
        return token;
    }
}
