package com.github.stark4j.web.aspect;

import com.github.stark4j.core.exception.Stark4jException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用JavaEE时将会初始化此类用于记录请求参数
 *
 * @author Allen Created 2020/4/1
 */
@Slf4j
@Aspect
@Component
@ConditionalOnClass(Servlet.class)
public class ServletWebLogAspect {

    /**
     * 用于保存每次请求的时间戳
     */
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        doBeforeSomething(joinPoint);
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        doAfterSomeThing();
    }

    @AfterThrowing(throwing = "ex", pointcut = "webLog()")
    public void exception(Throwable ex) {
        if (ex instanceof Stark4jException) {
            log.warn("Web层捕捉到业务异常,code: {},message: {}", ((Stark4jException) ex).getCode(), ex.getMessage());
        } else if (ex instanceof ValidationException) {
            log.warn("Web层捕捉到参数校验失败异常:{}", ex.getMessage());
        } else {
            log.error("Web层捕捉到异常", ex);
        }
        startTime.remove();
    }

    private void doBeforeSomething(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        //获取请求参数
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            log.info("--------------------------------------------------------");
            log.info("==> 请求地址: {}", request.getRequestURL().toString());
            log.info("==> 请求类型: {}", request.getMethod());
            log.info("==> IP: {}", request.getRemoteAddr());
            log.info("==> 请求头: {}", getHead(request));
            log.info("==> 匹配方法: {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            log.info("==> 方法参数: {}", Arrays.toString(joinPoint.getArgs()));
        } else {
            log.info("--------------------------------------------------------");
            log.warn("AOP失效，ServletRequestAttributes是空的.");
        }
    }

    private Map<String, Object> getHead(HttpServletRequest request) {
        Enumeration<String> enumeration = request.getHeaderNames();
        Map<String, Object> head = new HashMap<>(8);
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            head.put(name, request.getHeader(name));
        }
        return head;
    }


    private void doAfterSomeThing() {
        long time = System.currentTimeMillis() - startTime.get();
        log.info("<== 接口耗时: {}ms.", time);
        startTime.remove();
    }

}
