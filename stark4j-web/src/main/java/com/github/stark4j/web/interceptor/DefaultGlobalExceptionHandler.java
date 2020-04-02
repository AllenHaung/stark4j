package com.github.stark4j.web.interceptor;

import com.github.stark4j.beans.Stark4jCode;

/**
 * @author Allen Created 2020/4/2
 */
public class DefaultGlobalExceptionHandler extends AbstractGlobalExceptionHandler {

    @Override
    @SuppressWarnings("unchecked")
    protected Class<Stark4jCode> getResultCode() {
        return Stark4jCode.class;
    }
}
