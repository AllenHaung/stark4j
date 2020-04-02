package com.github.stark4j.beans.vo;

import com.github.stark4j.beans.Stark4jVersion;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 统一请求参数
 * @author Allen Created 2020/3/30
 */
@Data
public class OperationParam<T> implements Serializable {

    private static final long serialVersionUID = Stark4jVersion.STARK4J_SERIAL_VERSION_ID;

    /**
     * 预留字段，语言编码，默认为中文
     */
    private String lang;
    /**
     * 预留字段，是否为mock请求
     */
    private boolean mock;
    /**
     * 实际请求
     */
    @Valid
    @NotNull(message = "requestData can not be null")
    private T requestData;

    public static <T> OperationParam<T> create(T data){
        OperationParam<T> param = new OperationParam<>();
        param.setRequestData(data);
        return param;
    }

}
