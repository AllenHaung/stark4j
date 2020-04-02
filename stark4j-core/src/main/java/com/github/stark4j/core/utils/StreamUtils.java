package com.github.stark4j.core.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java8 Stream工具类
 * <p>
 * @author Allen Created 2020/3/27
 **/
public final class StreamUtils {
    /**
     * 将一个集合按照某种规则进行分组
     *
     * @param source 原数据
     * @param fun    处理Function
     * @param <T>    元数据类型
     * @param <K>    Key的类型
     * @return 处理后的结果
     */
    public static <K, T> Map<K, List<T>> groupBy(Collection<T> source, Function<T, K> fun) {
        return source.parallelStream().collect(Collectors.groupingBy(fun));
    }

    /**
     * 将一个集合转换成另一个数组(基于Fork/Join)
     *
     * @param source 原数据
     * @param fun    处理Function
     * @param <T>    元数据类型
     * @param <R>    返回类型
     * @return 处理后的结果
     */
    public static <T, R> Set<R> parallelTransformToSet(Collection<T> source, Function<T, R> fun) {
        return map(source, fun).collect(Collectors.toSet());
    }

    /**
     * 将一个集合转换成另一个数组
     *
     * @param source 原数据
     * @param fun    处理Function
     * @param <T>    元数据类型
     * @param <R>    返回类型
     * @return 处理后的结果
     */
    public static <T, R> Set<R> transformToSet(Collection<T> source, Function<T, R> fun) {
        return map(source, fun).collect(Collectors.toSet());
    }

    /**
     * 将一个集合转换成另一个数组(基于Fork/Join)
     *
     * @param source 原数据
     * @param fun    处理Function
     * @param <T>    元数据类型
     * @param <R>    返回类型
     * @return 处理后的结果
     */
    public static <T, R> List<R> parallelTransformToList(Collection<T> source, Function<T, R> fun) {
        return parallelMap(source, fun).collect(Collectors.toList());
    }

    /**
     * 将一个集合转换成另一个数组
     *
     * @param source 原数据
     * @param fun    处理Function
     * @param <T>    元数据类型
     * @param <R>    返回类型
     * @return 处理后的结果
     */
    public static <T, R> List<R> transformToList(Collection<T> source, Function<T, R> fun) {
        return map(source, fun).collect(Collectors.toList());
    }

    /**
     * 采用Push的方式处理Stream流
     *
     * @param source 原数据
     * @param fun    处理函数
     * @param <T>    元数据类型
     * @param <R>    返回类型
     * @return 返回处理后的流结果
     */
    public static <T, R> Stream<R> map(Collection<T> source, Function<T, R> fun) {
        return source.stream().map(fun);
    }

    /**
     * 采用Fork/Join的方式处理Stream流
     *
     * @param source 原数据
     * @param fun    处理函数
     * @param <T>    元数据类型
     * @param <R>    返回类型
     * @return 返回处理后的流结果
     */
    public static <T, R> Stream<R> parallelMap(Collection<T> source, Function<T, R> fun) {
        return source.parallelStream().map(fun);
    }

    /**
     * 可变参数创建一个Stream
     *
     * @param values 数据元素
     * @param <T>    元数据的类型
     * @return 返回Stream流
     */
    public static <T> Stream<T> ofStream(T... values) {
        return Stream.of(values);
    }
}
