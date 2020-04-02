package com.github.stark4j.core.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Excel操作工具类
 *
 * @author Allen Created 2020/3/27
 */
public final class IExcelUtils {

    //--------------------------------
    //      读取Excel
    //--------------------------------

    /**
     * 读取一个Excel文件
     *
     * @param inputStream 输入流
     * @param clazz       POJO类型
     * @param consumer    读取EXCEL数据的消费者
     */
    public static <T> void read(InputStream inputStream, Class<T> clazz, Consumer<List<T>> consumer) {
        EasyExcel.read(inputStream, clazz, new ExcelDataListener<>(consumer)).sheet().doRead();
    }

    /**
     * 读取一个Excel文件
     *
     * @param inputStream Excel文件输入流
     * @param clazz       POJO类型
     * @param consumer    读取EXCEL数据的消费者
     * @param batchCount  读取多少个文件后交由消费者处理
     */
    public static <T> void read(InputStream inputStream, Class<T> clazz, Consumer<List<T>> consumer, int batchCount) {
        EasyExcel.read(inputStream, clazz, new ExcelDataListener<>(batchCount, consumer)).sheet().doRead();
    }

    /**
     * 读取某个Excel的某个Sheet下的数据
     *
     * @param inputStream Excel文件输入流
     * @param clazz       POJO类型
     * @param consumer    读取EXCEL数据的消费者
     * @param sheetNo     Sheet的编号
     */
    public static <T> void readBySheet(InputStream inputStream, Class<T> clazz, Consumer<List<T>> consumer, int sheetNo) {
        readBySheet(inputStream, clazz, consumer, sheetNo, 2048);
    }

    /**
     * 读取某个Excel的某个Sheet下的数据
     *
     * @param inputStream Excel文件输入流
     * @param clazz       POJO类型
     * @param consumer    读取EXCEL数据的消费者
     * @param sheetNo     Sheet的编号
     * @param batchCount  批处理数量
     */
    public static <T> void readBySheet(InputStream inputStream, Class<T> clazz, Consumer<List<T>> consumer, int sheetNo, int batchCount) {
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(inputStream, clazz, new ExcelDataListener<>(batchCount, consumer)).build();
            ReadSheet readSheet = EasyExcel.readSheet(sheetNo).build();
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }
    }

    //--------------------------------
    //      生成Excel
    //--------------------------------

    /**
     * 生成一个Excel文件
     *
     * @param outputStream 生成的Excel文件输出流
     * @param clazz        POJO对象
     * @param sheetName    sheet的名称
     * @param data         Excel文件数据
     */
    public static <T> void write(OutputStream outputStream, Class<T> clazz, String sheetName, List<T> data) {
        EasyExcel.write(outputStream, clazz).sheet(sheetName).doWrite(data);
    }

    public static class ExcelDataListener<T> extends AnalysisEventListener<T> {
        private static final int BATCH_COUNT = 2048;
        private List<T> list;
        private Consumer<List<T>> consumer;

        public ExcelDataListener(Consumer<List<T>> consumer) {
            list = new ArrayList<>(BATCH_COUNT);
            this.consumer = consumer;
        }

        public ExcelDataListener(int batchCount, Consumer<List<T>> consumer) {
            if (batchCount < 1) {
                batchCount = 1;
            }
            list = new ArrayList<>(batchCount);
            this.consumer = consumer;
        }

        @Override
        public void invoke(T data, AnalysisContext analysisContext) {
            list.add(data);
            if (list.size() >= BATCH_COUNT) {
                consumer.accept(list);
                list.clear();
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            consumer.accept(list);
        }
    }

}
