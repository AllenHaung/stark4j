package com.github.stark4j.core.annotation;

import com.github.stark4j.core.support.startegy.StrategyParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记一个类为策略类
 * @author Allen Created 2020/4/1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MarkStrategy {

    /**
     * 策略值
     */
    String value();

    /**
     * 策略值解析器
     */
    Class<StrategyParser> strategyParser() default StrategyParser.class;

}
