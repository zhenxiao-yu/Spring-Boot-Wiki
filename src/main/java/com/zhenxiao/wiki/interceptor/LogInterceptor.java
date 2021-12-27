//package com.zhenxiao.wiki.interceptor;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * Interceptor：unique for Spring projects，commonly used for login verification,
// * access control and log printing
// */
//@Component
//public class LogInterceptor implements HandlerInterceptor {
//
//    private static final Logger LOG = LoggerFactory.getLogger(LogInterceptor.class);
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // print request message
//        LOG.info("---------------- LogInterceptor START ----------------");
//        LOG.info("request url: {} {}", request.getRequestURL().toString(), request.getMethod());
//        LOG.info("remote url: {}", request.getRemoteAddr());
//
//        long startTime = System.currentTimeMillis();
//        request.setAttribute("requestStartTime", startTime);
//        return true; //return true to continue interceptor operation
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        long startTime = (Long) request.getAttribute("requestStartTime");
//        LOG.info("---------------- LogInterceptor END | time elapsed：{} ms ----------------", System.currentTimeMillis() - startTime);
//    }
//}