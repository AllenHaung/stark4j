package com.github.stark4j.core.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * BASE64工具类
 *
 * @author Allen Created 2020/3/27
 */
public final class IBase64Utils {

    /**
     * 将字节流base64编码
     *
     * @param outputStream 字节流
     * @return 返回编码后的结果
     */
    public static String encode(ByteArrayOutputStream outputStream) {
        return String.join("", "data:image/jpg;base64,", Base64.encode(outputStream.toByteArray()));
    }

}
