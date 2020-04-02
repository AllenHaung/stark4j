package com.github.stark4j.beans.enums;

/**
 * @author Allen Created 2020/3/30
 */
public interface Stark4jEnums<T extends Enum<T>, V> {

    /**
     * 通过value获取某个枚举实例
     *
     * @param value 枚举值
     * @return 返回枚举实例
     */
    T getInstance(V value);

    /**
     * 返回枚举的值
     *
     * @return 自定义枚举的值
     */
    V getValue();

    /**
     * 返回枚举的名称
     *
     * @return 枚举名称
     */
    String name();

    /**
     * 获取该枚举的所有value
     *
     * @return 枚举的所有value值
     */
    T[] values();

}
