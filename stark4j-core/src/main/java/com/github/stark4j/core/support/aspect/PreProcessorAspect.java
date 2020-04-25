package com.github.stark4j.core.support.aspect;

import com.github.stark4j.core.annotation.PreProcessor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Allen Created 2020/4/19
 */
@Slf4j
@Aspect
@Component
public class PreProcessorAspect extends AbstractProcessorAspect {

    @Pointcut("@annotation(com.github.stark4j.core.annotation.PreProcessor)")
    public void processorAspect() {

    }

    @Before("processorAspect() && @annotation(preProcessor)")
    public void doBefore(JoinPoint joinPoint, PreProcessor preProcessor) {
        log.info("----------------[ pre processor ]----------------");
        List<Object> processors = getProcessors(preProcessor.value());
        doProcessor(processors, preProcessor.value(), joinPoint.getArgs());
    }

}
