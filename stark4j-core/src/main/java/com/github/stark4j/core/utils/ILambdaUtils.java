package com.github.stark4j.core.utils;

import com.github.stark4j.core.function.LambdaFunction;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Allen Created 2020/3/30
 */
public final class ILambdaUtils {

    private static Map<Class<?>, SerializedLambda> SERIALIZED_LAMBDA_CACHE = new ConcurrentHashMap<>();

    public static <T> String property(LambdaFunction<T, ?> serializable) {
        SerializedLambda lambda = resolve(serializable);
        return ReflectionUtils.methodToProperty(lambda.getImplMethodName());
    }

    public static <T> SerializedLambda resolve(LambdaFunction<T, ?> serializable) {
        return SERIALIZED_LAMBDA_CACHE.computeIfAbsent(serializable.getClass(), clazz -> {
            try {
                Method method = clazz.getDeclaredMethod("writeReplace");
                method.setAccessible(Boolean.TRUE);
                return (SerializedLambda) method.invoke(serializable);
            } catch (Exception e) {
                throw new LambdaException(e);
            }
        });
    }

}
