package com.github.stark4j.beans.vo;

import com.github.stark4j.beans.Stark4jCode;
import com.github.stark4j.beans.Stark4jVersion;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * 统一响应结果
 *
 * @author Allen Created 2020/3/30
 */
@Data
public class OperationResult<T> implements Serializable {

    private static final long serialVersionUID = Stark4jVersion.STARK4J_SERIAL_VERSION_ID;
    /**
     * 响应码
     */
    private String respCode;
    /**
     * 响应描述
     */
    private String respDesc;
    /**
     * 请求id，用于链路追踪
     */
    private String requestId;
    /**
     * 实际响应结果
     */
    private T respData;

    public OperationResult() {
    }

    public OperationResult(String respCode, String respDesc) {
        this(respCode, respDesc, null);
    }

    public OperationResult(String respCode, String respDesc, T respData) {
        this.respCode = respCode;
        this.respDesc = respDesc;
        this.respData = respData;
    }

    public static <T> OperationResult<T> returnOk() {
        return returnOk(null);
    }

    public static <T> OperationResult<T> returnOk(T data) {
        return new OperationResult<>(Stark4jCode.SUCCESS, "success", data);
    }

    public static <T> OperationResult<T> returnFail() {
        return returnFail(Stark4jCode.SERVER_ERROR, "Server Error");
    }

    public static <T> OperationResult<T> returnFail(String respCode, String respDesc) {
        return new OperationResult<>(respCode, respDesc);
    }

    public static boolean isSuccess(OperationResult result) {
        return Optional.ofNullable(result)
                .map(var -> Stark4jCode.SUCCESS.equals(var.getRespCode()))
                .orElse(false);
    }

    public static boolean notNull(OperationResult result) {
        return Optional.ofNullable(result)
                .map(var -> var.getRespData() != null)
                .orElse(false);
    }

    public static boolean isSuccessAndHasData(OperationResult result) {
        return notNull(result) && Objects.nonNull(result.getRespData());
    }

}
