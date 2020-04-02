package com.github.stark4j.web.config;

import com.github.stark4j.web.interceptor.AbstractGlobalExceptionHandler;
import com.github.stark4j.web.interceptor.DefaultGlobalExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Allen Created 2020/4/2
 */
@Configuration
public class Stark4jWebBoot {

    @Bean
    @ConditionalOnMissingBean(AbstractGlobalExceptionHandler.class)
    public DefaultGlobalExceptionHandler defaultGlobalExceptionHandler(){
        return new DefaultGlobalExceptionHandler();
    }

}
