package com.github.stark4j.core.exception;

import com.github.stark4j.beans.Stark4jCode;
import com.github.stark4j.beans.Stark4jVersion;

import java.io.Serializable;

/**
 * @author Allen Created 2020/4/2
 */
public class Stark4jException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = Stark4jVersion.STARK4J_SERIAL_VERSION_ID;
    
    private String code;

    public Stark4jException(String code, String message, Throwable e) {
        super(message, e);
        this.code = code;
    }

    public Stark4jException(String message, Throwable e) {
        this(Stark4jCode.SERVER_ERROR, message, e);
    }

    public Stark4jException(String code, String message) {
        this(code, message, null);
    }

    public Stark4jException(String message) {
        this(Stark4jCode.SERVER_ERROR, "系统服务异常");
    }

    public static Stark4jException create(String message, Throwable e) {
        return new Stark4jException(message, e);
    }

    public static Stark4jException create(String code, String message) {
        return new Stark4jException(code, message);
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.join("",
                this.getClass().getName(), ":",
                "[code:", this.code,
                ", message:", getMessage(), "]");
    }
}