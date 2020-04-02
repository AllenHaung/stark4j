package com.github.stark4j.web.constant;


import com.github.stark4j.core.annotation.FieldDesc;

/**
 * @author Allen Created 2020/4/1
 */
public class RequestHeadConstant {

    @FieldDesc("请求id，同一个链路请求中，x-request-id应该需要保持一直")
    public static final String X_REQUEST_ID = "x-request-id";

}
