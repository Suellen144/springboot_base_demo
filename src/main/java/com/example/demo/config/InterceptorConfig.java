package com.example.demo.config;

import com.example.demo.interceptor.CheckTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Description: 拦截器配置
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.config
 * @Author: Suellen
 * @CreateDate: 2021/12/20 10:43
 */
@Configuration
@EnableWebMvc
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    CheckTokenInterceptor checkTokenInterceptor;

    /**
     * 在这里配置需要拦截和放行的url*
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkTokenInterceptor)
                 //拦截
                .addPathPatterns("/**")
                 //放行
                .excludePathPatterns("/sysUser/login/**")
                .excludePathPatterns("/sysUser/getMessageCode/**")
                .excludePathPatterns("/sysUser/register/**")
                .excludePathPatterns("/sysUser/verifyPhoneAndMessageCode/**")
                .excludePathPatterns("/sysUser/resetPassword/**")
                .excludePathPatterns("/attachmentInfo/**")
                .excludePathPatterns("/dept/**")
                .excludePathPatterns("/accidentInfo/**")
                .excludePathPatterns("/file/**")
                .excludePathPatterns("/user/**")
                .excludePathPatterns("/userTest/**")
                .excludePathPatterns("/aopDemo/**")
                .excludePathPatterns("/info/**");
     }
 }