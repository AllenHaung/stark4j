package com.github.stark4j.core.utils;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Allen Created 2020/3/30
 */
public final class SerializeUtils {

    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    /**
     * 通过protostuff将对象序列化成字节数组
     *
     * @param object 需要序列化的对象
     * @param <T>    对象的泛型
     * @return 返回序列化后的字节数组
     */
    @SuppressWarnings("unchecked")
    public static <T> byte[] protoSerializer(T object) {
        Class<T> clazz = (Class<T>) object.getClass();
        Schema<T> schema = getSchema(clazz);
        return ProtobufIOUtil.toByteArray(object, schema, LinkedBuffer.allocate(256));
    }

    /**
     * 利用protostuff将字节数组反序列化为对象。需要反序列化的对象要有为空的构造器。
     *
     * @param bytes 字节数组
     * @param clazz 泛序列化后的对象的class
     * @param <T>   实际对象的泛型
     * @return 返回泛序列化后的对象
     */
    public static <T> T protoDeSerializer(byte[] bytes, Class<T> clazz) {
        T message;
        try {
            message = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        Schema<T> schema = getSchema(clazz);
        ProtobufIOUtil.mergeFrom(bytes, message, schema);
        return message;
    }

    /**
     * 使用jdk序列化的方式进行序列化
     *
     * @param object 需要序列化的对象
     * @return 返回序列化结果
     */
    public static byte[] jdkSerialize(Serializable object) {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(object);
            oos.flush();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to serialize object of type: " + object.getClass(), ex);
        }
        return baos.toByteArray();
    }

    /**
     * 使用jdk的方式进行反序列化
     *
     * @param bytes 字节数组
     * @param <T>   实际对象的泛型类型
     * @return 返回反序列化后的结果
     */
    @SuppressWarnings("unchecked")
    public static <T> T jdkDeSerializer(byte[] bytes) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Schema<T> getSchema(Class<T> clazz) {
        return (Schema<T>) cachedSchema.computeIfAbsent(clazz, key -> RuntimeSchema.createFrom(clazz));
    }
}
