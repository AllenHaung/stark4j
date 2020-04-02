package com.github.stark4j.core.utils;

import com.github.stark4j.beans.Stark4jCode;
import com.github.stark4j.core.exception.Stark4jException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author Allen Created 2020/3/30
 */
public final class JaxbUtils {

    /**
     * 将Java对象转换成UTF-8类型的xml字符串
     *
     * @param obj java bean
     * @return 返回生成的xml字符串
     */
    public static String beanToXml(Object obj) {
        return beanToXml(obj, "UTF-8");
    }

    /**
     * 将Java对象转换成xml
     *
     * @param obj      java bean
     * @param encoding xml字符格式
     * @return 返回生成的xml字符串
     */
    public static String beanToXml(Object obj, String encoding) {
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            //是否格式化输出
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //字符类型
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        } catch (Exception e) {
            throw Stark4jException.create(Stark4jCode.JAXB_ERROR, e);
        }
        return result;
    }


    /**
     * 将xml解析成bean对象
     *
     * @param xml   xml
     * @param clazz 目标类
     * @param <T>   泛型表示目标类
     * @return 返回bean对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T xmlToBean(String xml, Class<T> clazz) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            throw Stark4jException.create(Stark4jCode.JAXB_ERROR, e);
        }
        return t;
    }

}
