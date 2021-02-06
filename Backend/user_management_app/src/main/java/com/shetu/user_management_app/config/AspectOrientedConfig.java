package com.shetu.user_management_app.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class AspectOrientedConfig {

    @Before("execution(* com.shetu.user_management_app.controller.*.*(..))")
    public void logRequestInfo(JoinPoint joinPoint) {
        log.info("==========================  Server Request Starts ============================");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("REQUEST: URI : {} from address : {} with parameters: {}", request.getRequestURI(), request.getRemoteAddr(), joinPoint.getArgs());
        log.info("==========================  Server Request Ends ============================");
    }

}
