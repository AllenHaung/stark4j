package com.github.stark4j.core.utils;

import com.github.stark4j.beans.Stark4jCode;
import com.github.stark4j.core.annotation.FieldDesc;
import com.github.stark4j.core.exception.Stark4jException;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Allen Created 2020/4/1
 */
public final class IBeanUtils {

    private static final Map<Class<?>, Map<Class<?>, BeanCopier>> BEAN_COPIER_MAP = new HashMap<>();

    /**
     * 将一个对象的字段值复制给另外一个对象的字段
     *
     * @param source 源对象，携带字段值的对象
     * @param target 目标对象，需要赋值的对象
     */
    public static void copyProperties(Object source, Object target) {
        copyProperties(source, target, null);
    }

    public static void copyProperties(Object source, Object target, Converter converter) {
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        getBeanCopier(sourceClass, targetClass, converter).copy(source, target, converter);
    }

    public static BeanCopier getBeanCopier(Class<?> sourceClass, Class<?> targetClass, Converter converter) {
        Map<Class<?>, BeanCopier> copierMap = BEAN_COPIER_MAP
                .computeIfAbsent(sourceClass, k -> new HashMap<>(16));
        return copierMap.computeIfAbsent(targetClass,
                k -> BeanCopier.create(sourceClass, targetClass, !Objects.isNull(converter)));
    }

    /**
     * 将Java对象转换成map对象
     */
    public static Map<String, Object> beanToMap(Object bean) {
        Class<?> clazz = bean.getClass();
        try {
            Map<String, Object> map = new HashMap<>();
            for (Class<?> parent = clazz; parent != null; parent = parent.getSuperclass()) {
                Field[] fields = parent.getDeclaredFields();
                for (Field field : fields) {
                    boolean isAccessible = field.isAccessible();
                    field.setAccessible(true);
                    Object obj = field.get(bean);
                    if (obj != null) {
                        map.put(field.getName(), obj);
                    }
                    field.setAccessible(isAccessible);
                }
            }
            return map;
        } catch (Exception e) {
            throw Stark4jException.create(Stark4jCode.REFLECTION_ERROR, e);
        }
    }

    /**
     * 将Map对象转换成bean对象
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        try {
            T data = clazz.newInstance();
            for (Class<?> parent = clazz; parent != null; parent = parent.getSuperclass()) {
                Field[] fields = parent.getDeclaredFields();
                for (Field field : fields) {
                    Object value = map.get(field.getName());
                    //只有类型相同的才处理
                    if (field.getGenericType().toString().equals(value.getClass().getName())) {
                        boolean isAccessible = field.isAccessible();
                        field.setAccessible(true);
                        field.set(data, value);
                        field.setAccessible(isAccessible);
                    }
                }
            }
            return data;
        } catch (Exception e) {
            throw Stark4jException.create(Stark4jCode.REFLECTION_ERROR, e);
        }
    }

    /**
     * 获取某个class 字段值与描述
     *
     * @param clazz class对象
     * @return 返回解析结果
     */
    public static Map<Object, Object> getDescription(Class<?> clazz) {
        try {
            Map<Object, Object> map = new HashMap<>();
            for (Class<?> parent = clazz; parent != null; parent = parent.getSuperclass()) {
                Field[] fields = parent.getDeclaredFields();
                for (Field field : fields) {
                    boolean isAccessible = field.isAccessible();
                    field.setAccessible(true);
                    FieldDesc description = field.getAnnotation(FieldDesc.class);
                    if (description != null) {
                        map.put(field.get(clazz), description.value());
                    }
                    field.setAccessible(isAccessible);
                }
            }
            return map;
        } catch (Exception e) {
            throw Stark4jException.create(Stark4jCode.REFLECTION_ERROR, e);
        }
    }


}
