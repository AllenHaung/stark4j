package com.github.stark4j.core.utils;

import com.github.stark4j.beans.Stark4jCode;
import com.github.stark4j.core.exception.Stark4jException;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.Consumer;

/**
 * @author Allen Created 2020/4/2
 */
public final class IOUtils {

    //-------------------------
    //          文件操作
    //-------------------------

    /**
     * 获取某个文件
     *
     * @param path 文件的路径
     * @return 返回文件信息
     */
    public static File parse(String path) {
        return new File(path);
    }

    /**
     * 判断某个路径是否存在
     *
     * @param path 文件的路径
     * @return true表示存在, false表示不存在
     */
    public static boolean exists(String path) {
        return exists(parse(path));
    }

    /**
     * 判断某个文件是否存在
     *
     * @param file 文件信息
     * @return true表示存在, false表示不存在
     */
    public static boolean exists(File file) {
        return file.exists();
    }

    /**
     * 判断目标目录是否是一个文件
     *
     * @param path 目标目录
     * @return true表示为是一个文件，false表示为非文件
     */
    public static boolean isFile(String path) {
        return isFile(parse(path));
    }

    /**
     * 判断此File对象是否是一个文件
     *
     * @param file 需要判断的File对象
     * @return true表是为文件, false表示为非文件
     */
    public static boolean isFile(File file) {
        if (!exists(file)) {
            throw Stark4jException.create(Stark4jCode.DATA_NOT_FOUND,
                    String.join("", "file not exists: ", file.getAbsolutePath()));
        }
        return file.isFile();
    }

    /**
     * 判断此文件路径是否是一个文件夹
     *
     * @param path 需要判断的路径
     * @return true表是为文件夹, false表示为非文件夹
     */
    public static boolean isDirectory(String path) {
        return isDirectory(parse(path));
    }

    /**
     * 判断此File对象是否是一个文件夹
     *
     * @param file 需要判断的File对象
     * @return true表是为文件夹, false表示为非文件夹
     */
    public static boolean isDirectory(File file) {
        if (!exists(file)) {
            throw Stark4jException.create(Stark4jCode.DATA_NOT_FOUND,
                    String.join("", "directory not exists: ", file.getAbsolutePath()));
        }
        return file.isDirectory();
    }

    /**
     * 获取目标文件。如果文件存在，则直接返回，如果文件不存在，则会尝试新建
     *
     * @param path 文件的地址
     * @return 返回文件对象
     * @throws IOException 当文件操作失败时将会抛出此异常
     */
    public static File getOrMakeFile(String path) throws IOException {
        File file = new File(path);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            if (!parent.mkdirs()) {
                throw Stark4jException.create(Stark4jCode.IO_ERROR, String.join("", "mkdir error.path: ", parent.getAbsolutePath()));
            }
        }
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw Stark4jException.create(Stark4jCode.IO_ERROR, String.join("", "make file error.path: ", parent.getAbsolutePath()));
            }
        }
        return file;
    }

    //-------------------------
    //          IO操作
    //-------------------------

    /**
     * 将文件从源目录拷贝至目标目录
     *
     * @param source 文件源目录
     * @param target 文件目标目录
     * @throws IOException 文件写入失败时，将抛出此异常
     */
    public static void copy(String source, String target) throws IOException {
        try (FileChannel inputChannel = FileChannel.open(Paths.get(source),
                StandardOpenOption.READ); FileChannel outputChannel = FileChannel.open(Paths.get(target),
                StandardOpenOption.WRITE,
                StandardOpenOption.READ,
                StandardOpenOption.CREATE_NEW)) {
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        }
    }

    /**
     * 通过FileChannel的方式读取某个文件,并将读取的结果写入到ByteArrayOutputStream中
     *
     * @param path 文件地址
     * @return 返回读取后的输出流
     */
    public static ByteArrayOutputStream channelRead(String path) throws IOException {
        return channelRead(new FileInputStream(path));
    }

    /**
     * 通过FileChannel的方式读取某个文件,并将读取的结果写入到ByteArrayOutputStream中
     *
     * @param inputStream 文件输入流
     * @return 返回读取后的输出流
     * @throws IOException 文件操作异常时抛出
     */
    public static ByteArrayOutputStream channelRead(FileInputStream inputStream) throws IOException {
        return channelRead(inputStream, 4096);
    }

    /**
     * 通过FileChannel的方式读取某个文件,并将读取的结果写入到ByteArrayOutputStream中
     *
     * @param inputStream 文件输入流
     * @param capacity    Buffer容量大小
     * @return 返回读取后的输出流
     * @throws IOException 文件操作异常时抛出
     */
    public static ByteArrayOutputStream channelRead(FileInputStream inputStream, int capacity) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        channelRead(inputStream, capacity, outputStream::write);
        return outputStream;
    }

    /**
     * 通过FileChannel的方式读取某个文件，并将文件读取的结果写入输出流
     *
     * @param fileChannel  文件输入channel
     * @param outputStream 输出流对象
     * @throws IOException 文件操作异常时抛出
     */
    public static void channelRead(FileChannel fileChannel, OutputStream outputStream) throws IOException {
        channelRead(fileChannel, 4096, outputStream);
    }

    /**
     * 通过FileChannel的方式读取某个文件，并将文件读取的结果写入输出流
     *
     * @param fileChannel  文件输入channel
     * @param capacity     buffer容量
     * @param outputStream 输出流对象
     * @throws IOException 文件操作异常时抛出
     */
    public static void channelRead(FileChannel fileChannel, int capacity, OutputStream outputStream) throws IOException {
        channelRead(fileChannel, capacity, data -> {
            try {
                outputStream.write(data);
            } catch (IOException e) {
                throw Stark4jException.create(Stark4jCode.IO_ERROR, e);
            }
        });
    }

    /**
     * 通过FileChannel的方式读取某个文件，并将读取的字节交给consumer处理。
     * buffer的默认容量为4096
     *
     * @param inputStream 文件输入流
     * @param consumer    消费者
     * @throws IOException 文件操作异常时抛出
     */
    public static void channelRead(FileInputStream inputStream, Consumer<Byte> consumer) throws IOException {
        channelRead(inputStream, 4096, consumer);
    }

    /**
     * 通过FileChannel的方式读取某个文件，并将读取的字节交给consumer处理
     *
     * @param inputStream 文件输入流
     * @param capacity    buffer容量
     * @param consumer    消费者
     * @throws IOException 文件操作异常时抛出
     */
    public static void channelRead(FileInputStream inputStream, int capacity, Consumer<Byte> consumer) throws IOException {
        try (FileChannel channel = inputStream.getChannel()) {
            channelRead(channel, capacity, consumer);
        }
    }

    /**
     * 通过FileChannel的方式读取某个文件，并将读取的字节交给consumer处理。
     * buffer的默认容量为4096
     *
     * @param path     文件地址
     * @param consumer 消费者
     * @throws IOException 文件操作异常时抛出
     */
    public static void channelRead(String path, Consumer<Byte> consumer) throws IOException {
        channelRead(path, 4096, consumer);
    }

    /**
     * 通过FileChannel的方式读取某个文件，并将读取的字节交给consumer处理.
     *
     * @param path     文件地址
     * @param capacity buffer容量
     * @param consumer 消费者
     * @throws IOException 文件操作异常时抛出
     */
    public static void channelRead(String path, int capacity, Consumer<Byte> consumer) throws IOException {
        try (FileChannel fileChannel = getChannel(path, StandardOpenOption.READ)) {
            channelRead(fileChannel, capacity, consumer);
        }
    }

    /**
     * 通过FileChannel的方式读取某个文件，并将读取的字节交给consumer处理.
     * buffer的容量为4096.
     *
     * @param channel  文件输入channel
     * @param consumer 消费者
     * @throws IOException 文件操作异常时抛出
     */
    public static void channelRead(FileChannel channel, Consumer<Byte> consumer) throws IOException {
        channelRead(channel, 4096, consumer);
    }

    /**
     * 通过Channel的方式读取某个文件，并将读取的字节交给consumer处理
     *
     * @param channel  文件输入channel
     * @param capacity buffer容量
     * @param consumer 消费者
     * @throws IOException 文件操作异常时抛出
     */
    public static void channelRead(FileChannel channel, int capacity, Consumer<Byte> consumer) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
        while (channel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                byteBuffer.get(new byte[byteBuffer.limit()], 0, byteBuffer.limit());
                consumer.accept(byteBuffer.get());
            }
            byteBuffer.clear();
        }
    }


    /**
     * 获取某个路径的FileChannel
     *
     * @param path    文件路径地址
     * @param options 操作选项
     * @return 返回此路径的FileChannel
     * @throws IOException 文件操作异常时抛出
     */
    public static FileChannel getChannel(String path, OpenOption... options) throws IOException {
        return FileChannel.open(Paths.get(path), options);
    }

    public static void channelWrite(InputStream inputStream, String path) {

    }


}
