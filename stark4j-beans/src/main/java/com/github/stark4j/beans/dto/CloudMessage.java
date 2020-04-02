package com.github.stark4j.beans.dto;

import com.github.stark4j.beans.Stark4jVersion;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 消息队列数据传输对象
 *
 * @author Allen Created 2020/3/30
 */
@Data
public class CloudMessage<T> implements Serializable {

    private static final long serialVersionUID = Stark4jVersion.STARK4J_SERIAL_VERSION_ID;
    /**
     * 请求ID
     */
    private String requestId;
    /**
     * 生产者，一般为服务名
     */
    private String producer;
    /**
     * 消息发送时间
     */
    private LocalDateTime createdDate;
    /**
     * 具体的消息内容
     */
    private T data;

    public static <T> CloudMessage<T> create(T data) {
        return create(null, data);
    }

    public static <T> CloudMessage<T> create(String produce, T data) {
        return create(produce, LocalDateTime.now(), data);
    }

    public static <T> CloudMessage<T> create(String producer, LocalDateTime createdDate, T data) {
        CloudMessage<T> message = new CloudMessage<>();
        message.setProducer(producer);
        message.setCreatedDate(createdDate);
        message.setData(data);
        return message;
    }

}
