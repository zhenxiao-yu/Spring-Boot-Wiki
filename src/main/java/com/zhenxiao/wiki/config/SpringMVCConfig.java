//package com.zhenxiao.wiki.config;
//
//import com.zhenxiao.wiki.interceptor.LogInterceptor;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import javax.annotation.Resource;
//
//public class SpringMVCConfig implements WebMvcConfigurer {
//
//    @Resource
//    LogInterceptor logInterceptor;
//
//    public void addInterceptors(InterceptorRegistry registry) {
//        // using log interceptor
//        registry.addInterceptor(logInterceptor)
//                .addPathPatterns("/**").excludePathPatterns("/login");
//    }
//}
