package com.zhenxiao.wiki.controller;

import com.zhenxiao.wiki.exception.BusinessException;
import com.zhenxiao.wiki.response.CommonRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * validation exception handling
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public CommonRes validExceptionHandler(BindException e) {
        CommonRes commonResp = new CommonRes();
        LOG.warn("Validation Failed：{}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return commonResp;
    }

    /**
     * validation exception handling
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public CommonRes validExceptionHandler(BusinessException e) {
        CommonRes commonRes = new CommonRes();
        LOG.warn("Business Error：{}", e.getCode().getDesc());
        commonRes.setSuccess(false);
        commonRes.setMessage(e.getCode().getDesc());
        return commonRes;
    }

    /**
     * validation exception handling
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonRes validExceptionHandler(Exception e) {
        CommonRes commonResp = new CommonRes();
        LOG.error("System Error：", e);
        commonResp.setSuccess(false);
        commonResp.setMessage("系统出现异常，请联系管理员");
        return commonResp;
    }
}
