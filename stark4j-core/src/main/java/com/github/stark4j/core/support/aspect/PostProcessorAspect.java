package com.github.stark4j.core.support.aspect;

import com.github.stark4j.core.annotation.FinallyPostProcessor;
import com.github.stark4j.core.annotation.PostProcessor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Allen Created 2020/4/19
 */
@Slf4j
@Aspect
@Component
public class PostProcessorAspect extends AbstractProcessorAspect {

    @Pointcut("@annotation(com.github.stark4j.core.annotation.PostProcessor)")
    public void processorAspect() {

    }

    @Pointcut("@annotation(com.github.stark4j.core.annotation.FinallyPostProcessor)")
    public void finallyPostProcessorAspect() {

    }

    @AfterReturning("processorAspect() && @annotation(postProcessor)")
    public void doAfter(JoinPoint joinPoint, PostProcessor postProcessor) {
        log.info("----------------[ post processor ]----------------");
        List<Object> processors = getProcessors(postProcessor.value());
        doProcessor(processors, postProcessor.value(), joinPoint.getArgs());
    }

    @After("finallyPostProcessorAspect() && @annotation(finallyPostProcessor)")
    public void finallyDoAfter(JoinPoint joinPoint, FinallyPostProcessor finallyPostProcessor) {
        log.info("----------------[ finally post processor ]----------------");
        List<Object> processors = getProcessors(finallyPostProcessor.value());
        doProcessor(processors, finallyPostProcessor.value(), joinPoint.getArgs());
    }
}
