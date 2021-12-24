package com.example.demo.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 返回值实体
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.common
 * @Author: Suellen
 * @CreateDate: 2021/12/20 11:11
 */
public class R extends HashMap<String,Object> {

    private static final long serialVersionUID = 1L;
    public static final String CODE = "code";
    public static final String MSG = "message";
    public static final String DATA = "data";

    public R() {
        put(CODE, HttpStatus.SUCCESS);
        put(MSG, HttpMessage.SUCCESS);
    }
    public static R error () {
        return error(HttpStatus.ERROR, "未知异常,请联系管理员 ");
    }
    public static R error (String msg){
        return error(HttpStatus.ERROR, msg);
    }
    public static R error ( int code, String msg){
        R r = new R();
        r.put(CODE, HttpStatus.ERROR);
        r.put(MSG,msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put(MSG, msg);
        return r;
    }
    public static R ok(Map<String,Object> map){
        R r = new R();
        r.putAll(map);
        return r;
    }
    public static R ok(int code,String msg,Object obj) {
        Map map = new HashMap<>(3);
        map.put(CODE,code);
        map.put(MSG,msg);
        map.put(DATA,obj);
        return R.ok(map);
    }

    public static R ok(int code,String msg) {
        Map map = new HashMap<>(2);
        map.put(CODE,code);
        map.put(MSG,msg);
        return R.ok(map);
    }

    public static R ok(){
        return new R();
    }
    @Override
    public R put(String key,Object value) {
        super.put(key, value);
        return this;
    }
}
