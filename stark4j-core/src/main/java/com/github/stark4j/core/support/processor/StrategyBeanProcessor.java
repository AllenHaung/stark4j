package com.github.stark4j.core.support.processor;

import com.github.stark4j.beans.Stark4jCode;
import com.github.stark4j.core.annotation.MarkStrategy;
import com.github.stark4j.core.annotation.MarkStrategyInterface;
import com.github.stark4j.core.exception.Stark4jException;
import com.github.stark4j.core.factory.StrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author Allen Created 2020/4/19
 */
@Slf4j
@Component
public class StrategyBeanProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean,@NonNull String beanName) throws BeansException {
        Class<?> clazz = AopUtils.isAopProxy(bean) ? AopUtils.getTargetClass(bean) : bean.getClass();
        MarkStrategy antSpaceStrategy = AnnotationUtils.findAnnotation(clazz, MarkStrategy.class);
        if (antSpaceStrategy != null) {
            for (Class<?> parent = clazz; parent != null; parent = parent.getSuperclass()) {
                for (Class<?> anInterface : parent.getInterfaces()) {
                    if (anInterface.getAnnotation(MarkStrategyInterface.class) != null) {
                        if (log.isDebugEnabled()) {
                            log.debug("==> 注册策略类型: {},策略值:{},策略实体:{}", anInterface.getName(), antSpaceStrategy.value(), clazz.getName());
                        }
                        String strategyName = antSpaceStrategy.value();
                        StrategyFactory.getStrategy(anInterface, antSpaceStrategy.value()).ifPresent(var -> {
                            throw Stark4jException.create(Stark4jCode.DATA_EXISTED,
                                    String.join("", "策略冲突.策略名称:",
                                            strategyName,
                                            "策略类: ",
                                            var.getClass().getName(),
                                            " <--> ",
                                            bean.getClass().getName()));
                        });
                        StrategyFactory.addStrategy(anInterface, antSpaceStrategy.value(), bean);
                    }
                }
            }
        }
        return bean;
    }

}
