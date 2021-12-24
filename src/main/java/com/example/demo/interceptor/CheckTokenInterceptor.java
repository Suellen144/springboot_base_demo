package com.example.demo.interceptor;

import com.example.demo.common.HttpStatus;
import com.example.demo.common.R;
import com.example.demo.utils.CacheSingleton;
import com.example.demo.utils.RedisUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: token的检验拦截器
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.config
 * @Author: Suellen
 * @CreateDate: 2021/12/20 10:34
 */
@Component
public class CheckTokenInterceptor implements HandlerInterceptor {

    @Resource
    RedisUtils redisUtils;

    private static ThreadLocal<String> token = new ThreadLocal<>();
    private static ThreadLocal<String> userId = new ThreadLocal<>();

    /**
     * 签名使用的秘钥
     */
    final static String SECRET_KEY = "SuellenAndZhouhuakun144AlonginfoIPhone13AandMSIAndMeiandvulgar";

    /**
     * 放行options请求
     */
    final String OPTIONS = "OPTIONS";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //放行options请求
        String method = request.getMethod();
        if (OPTIONS.equalsIgnoreCase(method)) {
            return true;
        }
        String token = request.getHeader("token");
        token = StringUtils.isBlank(token) ? getCookie(request, "token") : token;
        boolean checkToken = false;
        if (token == null) {
            //没有token请去登陆
            doResponse(response, R.error(HttpStatus.ERROR, "校验不通过"));
        }else {
            //验证token
            try {
                //解密的key和加密的key必须一致
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(SECRET_KEY);
            //token正确且在有效期内正常执行，否则抛出异常
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            String userId = (String) claims.get("userId");
            boolean hasKey = redisUtils.hasKey(userId);
            String oldToken = hasKey?redisUtils.get(userId).toString():"";
            if ( !oldToken.equals("") && !oldToken.equals(token)) {
                //用户多端登录，踢掉旧的
                throw new UnsupportedJwtException("校验不通过");
            }
            //userIdCache.put(userId,token);
            CheckTokenInterceptor.putUserId(userId);
            checkToken = true;
            }catch (ExpiredJwtException e) {
                //token过期请去登陆
                doResponse(response,R.error(HttpStatus.ERROR,"校验不通过"));}
            catch (UnsupportedJwtException e) {
                // token校验失败请去登陆
                doResponse(response,R.error(HttpStatus.ERROR,"校验不通过"));}
            catch (Exception e) {
                //未知的token异常
                doResponse(response,R.error(HttpStatus.ERROR,"您的账户在别的地方登录了，请重新登录"));
            }
        }
        return checkToken;
    }

    private String getCookie(HttpServletRequest request,String name) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            if (name.equalsIgnoreCase(cookies[i].getName())) {
                return cookies[i].getValue();
            }
        }
        return null;
    }

    private void setCookie(HttpServletResponse response,String name,String value){
        Cookie cookie = new Cookie(name,value);
        //cookie.setDomain(“*.csg.cn);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*30);
        response.addCookie(cookie);
    }
    public static void putToken(String token){
        CheckTokenInterceptor.token.set(token);}
    public static String getToken(){
        return CheckTokenInterceptor.token.get();
    }
    public static void putUserId(String userId){
        CheckTokenInterceptor.userId.set(userId);
    }


    public static String getUserId() { return CheckTokenInterceptor.userId.get();}
    private void doResponse(HttpServletResponse response,R resultVo) throws IOException {
        String token = CheckTokenInterceptor.getToken();
        setCookie(response,"token" , token);
        response.setContentType( "application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String result = new ObjectMapper().writeValueAsString(resultVo);
        out.print(result);
        CheckTokenInterceptor.token.remove();
        CheckTokenInterceptor.userId.remove();
        out.flush();
        out.close();
    }
}
