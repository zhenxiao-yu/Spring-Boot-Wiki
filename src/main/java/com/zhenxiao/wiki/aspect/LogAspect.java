package com.zhenxiao.wiki.aspect;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

//import com.zhenxiao.wiki.util.RequestContext;
//import com.zhenxiao.wiki.util.SnowFlake;

@Aspect
@Component
public class LogAspect {

    private final static Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    // define a point cut
    @Pointcut("execution(public * com.zhenxiao.*.controller..*Controller.*(..))")
    public void controllerPointcut() {
    }

    // @Resource
    // private SnowFlake snowFlake;

    @Before("controllerPointcut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        // add log id
        // MDC.put("LOG_ID", String.valueOf(snowFlake.nextId()));

        // start printing request log
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();

        // print request information
        LOG.info("------------- START -------------");
        LOG.info("request url: {} {}", request.getRequestURL().toString(), request.getMethod());
        LOG.info(": {}.{}", signature.getDeclaringTypeName(), name);
        LOG.info("remote url: {}", request.getRemoteAddr());

        // RequestContext.setRemoteAddr(getRemoteIp(request));

        // print request arguments
        Object[] args = joinPoint.getArgs();
        // LOG.info("request argument: {}", JSONObject.toJSONString(args));
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest
                    || args[i] instanceof ServletResponse
                    || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }
        // exclude sensitive info and arguments that are too long
        String[] excludeProperties = {"password", "file"}; //excluding password and file
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
        excludefilter.addExcludes(excludeProperties);
        LOG.info("request argument: {}", JSONObject.toJSONString(arguments, excludefilter));
    }


    @Around("controllerPointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        // proceed with operation
        Object result = proceedingJoinPoint.proceed();
        // exclude sensitive info and arguments that are too long
        String[] excludeProperties = {"password", "file"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
        excludefilter.addExcludes(excludeProperties);
        LOG.info("Result: {}", JSONObject.toJSONString(result, excludefilter));
        LOG.info("------------- END | time elapsed：{} ms -------------", System.currentTimeMillis() - startTime);
        return result;
    }

    /**
     * using nginx
     *
     * @param request
     * @return
     */
    public String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
