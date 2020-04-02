package com.github.stark4j.core.support.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

/**
 * @author Allen Created 2020/4/1
 */
@Slf4j
public class DefaultApplicationManager implements ApplicationManager, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public String getActiveProfile() {
        return applicationContext.getEnvironment().getActiveProfiles()[0].toUpperCase();
    }

    @Override
    public boolean isProduce() {
        String activeProfile = getActiveProfile();
        if (log.isDebugEnabled()) {
            log.debug("当前环境:{}", activeProfile);
        }
        return "PROD".equals(activeProfile);
    }

    @Override
    public String getApplicationName() {
        return applicationContext.getEnvironment().getProperty("spring.application.name");
    }

}
