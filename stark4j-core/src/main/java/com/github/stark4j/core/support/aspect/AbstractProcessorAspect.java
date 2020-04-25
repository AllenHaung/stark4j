package com.github.stark4j.core.support.aspect;

import com.github.stark4j.beans.Stark4jCode;
import com.github.stark4j.core.annotation.ProcessorMethod;
import com.github.stark4j.core.exception.Stark4jException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Allen Created 2020/4/19
 */
@Slf4j
public class AbstractProcessorAspect implements Ordered, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final Map<Class<?>, List<Object>> cache = new ConcurrentHashMap<>();

    protected void doProcessor(List<Object> processors, Class<?> clazz, Object[] args) {
        log.info("==> [AOP]processor size: {}", processors.size());
        Method processorMethod = getProcessorMethod(clazz);
        if (processorMethod != null) {
            for (Object processor : processors) {
                try {
                    processorMethod.invoke(processor, args);
                } catch (Exception e) {
                    log.error("do processor error", e);
                    throw new Stark4jException(Stark4jCode.REFLECTION_ERROR, "invoke method error");
                }
            }
        } else {
            log.warn("==> [AOP]{} not mark processor method.", clazz.getName());
        }
    }

    protected Method getProcessorMethod(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        if (methods.length == 1) {
            return methods[0];
        }
        for (Method method : methods) {
            if (method.getAnnotation(ProcessorMethod.class) != null) {
                return method;
            }
        }
        return null;
    }

    protected List<Object> getProcessors(Class<?> clazz) {
        return cache.computeIfAbsent(clazz, k -> {
                    Map<String, ?> beans = applicationContext.getBeansOfType(k);
                    List<Object> bean = new ArrayList<>(beans.size());
                    beans.forEach((key, value) -> bean.add(value));

                    //如果有排序，则进行排序
                    if (Ordered.class.isAssignableFrom(k)) {
                        bean.sort(Comparator.comparingInt(var -> ((Ordered) var).getOrder()));
                    }
                    return bean;
                }
        );
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
