package com.github.stark4j.core.utils;

import com.github.stark4j.core.exception.Stark4jException;
import com.github.stark4j.core.framework.PropertyConvert;
import com.github.stark4j.core.function.LambdaFunction;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Allen Created 2020/3/30
 */
public final class ILambdaUtils {

    private static final Map<Class<?>, SerializedLambda> SERIALIZED_LAMBDA_CACHE = new ConcurrentHashMap<>();

    /**
     * 通过lambda表达式读取字段的名称
     *
     * @param serializable LambdaFunction
     * @param <T>          类的泛型
     * @return 返回读取的方法名称
     */
    public static <T> String property(LambdaFunction<T, ?> serializable) {
        SerializedLambda lambda = resolve(serializable);
        return IReflectionUtils.methodToProperty(lambda.getImplMethodName());
    }

    /**
     * 通过lambda表达式读取字段的名称
     *
     * @param serializable LambdaFunction
     * @param convert      属性转换
     * @param <T>          类的泛型
     * @return 返回读取的方法名称
     */
    public static <T> String property(LambdaFunction<T, ?> serializable, PropertyConvert convert) {
        String name = property(serializable);
        return convert.convert(name);
    }

    public static <T> SerializedLambda resolve(LambdaFunction<T, ?> serializable) {
        return SERIALIZED_LAMBDA_CACHE.computeIfAbsent(serializable.getClass(), clazz -> {
            try {
                Method method = clazz.getDeclaredMethod("writeReplace");
                method.setAccessible(Boolean.TRUE);
                return (SerializedLambda) method.invoke(serializable);
            } catch (Exception e) {
                throw new Stark4jException("500", e);
            }
        });
    }

}
