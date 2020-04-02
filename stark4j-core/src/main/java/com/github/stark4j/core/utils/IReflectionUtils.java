package com.github.stark4j.core.utils;

import com.github.stark4j.beans.Stark4jCode;
import com.github.stark4j.core.exception.Stark4jException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

/**
 * @author Allen Created 2020/4/2
 */
public class IReflectionUtils {

    public static boolean isEmptyObject(Object bean) {
        Class<?> clazz = bean.getClass();
        try {
            for (Class<?> parent = clazz; parent != null; parent = parent.getSuperclass()) {
                Field[] fields = parent.getDeclaredFields();
                for (Field field : fields) {
                    boolean isAccessible = field.isAccessible();
                    field.setAccessible(true);
                    Object obj = field.get(bean);
                    field.setAccessible(isAccessible);
                    if (obj != null) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * 通过反射的方式创建一个对象实例
     *
     * @param clazz 类对象
     * @param <T>   对象的类型
     * @return 返回对象实例
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new Stark4jException(Stark4jCode.NEW_INSTANCE_FAIL, "new instance fail.", e);
        }
    }

    public static <T> T newInstance(Class<T> clazz, Object... param) {
        if (param == null || param.length == 0) {
            return newInstance(clazz);
        }
        try {
            Constructor<T> constructor = clazz.getConstructor(getClasses(param));
            boolean accessible = constructor.isAccessible();
            constructor.setAccessible(true);
            T instance = constructor.newInstance(param);
            constructor.setAccessible(accessible);
            return instance;
        } catch (NoSuchMethodException e) {
            throw new Stark4jException(Stark4jCode.NOT_FOUND_CONSTRUCTOR, "not fond constructor", e);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new Stark4jException(Stark4jCode.NEW_INSTANCE_FAIL, "constructor new instance fail.", e);
        }
    }

    public static Class<?>[] getClasses(Object... param) {
        Class<?>[] classes = new Class[param.length];
        for (int i = 0; i < param.length; i++) {
            classes[i] = param[i].getClass();
        }
        return classes;
    }

    /**
     * 通过get set方法名称获取属性的名称
     *
     * @param name 方法名称
     * @return 返回属性名称
     */
    public static String methodToProperty(String name) {
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else if (name.startsWith("get") || name.startsWith("set")) {
            name = name.substring(3);
        } else {
            throw new Stark4jException(Stark4jCode.SERVER_ERROR, "不是get、set、is方法");
        }

        if (name.length() == 1 || (name.length() > 1 && !Character.isUpperCase(name.charAt(1)))) {
            name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }

        return name;
    }

    public static boolean isProperty(String name) {
        return isGetter(name) || isSetter(name);
    }

    /**
     * 是否是一个get方法
     */
    public static boolean isGetter(String name) {
        return (name.startsWith("get") && name.length() > 3) || (name.startsWith("is") && name.length() > 2);
    }

    /**
     * 是否是一个set方法
     */
    public static boolean isSetter(String name) {
        return name.startsWith("set") && name.length() > 3;
    }
}