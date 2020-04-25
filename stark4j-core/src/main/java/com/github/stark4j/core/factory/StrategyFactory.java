package com.github.stark4j.core.factory;

import com.github.stark4j.beans.Stark4jCode;
import com.github.stark4j.core.exception.Stark4jException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Allen Created 2020/4/1
 */
public class StrategyFactory {
    /**
     * 存储策略对象的容器
     */
    private static final Map<Class<?>, Map<String, Object>> BEANS = new HashMap<>();

    /**
     * 新增一个策略
     *
     * @param strategyType 策略类型
     * @param type         策略名称
     * @param bean         策略实例
     */
    public static void addStrategy(Class<?> strategyType, String type, Object bean) {
        BEANS.computeIfAbsent(strategyType, k -> new HashMap<>())
                .put(type, bean);
    }

    /**
     * 获取某个策略
     *
     * @param strategyType 策略类型
     * @param type         策略名称
     * @return 返回策略实例
     */
    @SuppressWarnings("unchecked")
    public static <T> Optional<T> getStrategy(Class<T> strategyType, String type) {
        return Optional.ofNullable(BEANS.get(strategyType))
                .map(var -> (T) var.get(type));
    }

    /**
     * 查询某个策略是否存在
     *
     * @param strategyType 策略接口类型
     * @param name         策略名称
     * @return 返回是否存在
     */
    public static boolean exists(Class<?> strategyType, String name) {
        return Optional.ofNullable(BEANS.get(strategyType))
                .map(var -> var.get(name))
                .isPresent();
    }

    /**
     * 获取某个策略,若未找到策略，则抛出未找到策略异常
     *
     * @param strategyType 策略类型
     * @param type         策略名称
     * @return 返回策略实例
     */
    public static <T> T getNonNullStrategy(Class<T> strategyType, String type) {
        return getStrategy(strategyType, type)
                .orElseThrow(() -> new Stark4jException(Stark4jCode.NOT_FOUND_STRATEGY, "未找到策略方法"));
    }
}
