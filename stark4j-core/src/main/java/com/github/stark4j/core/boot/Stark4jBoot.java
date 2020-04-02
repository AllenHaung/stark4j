package com.github.stark4j.core.boot;

import com.github.stark4j.core.annotation.MarkStrategy;
import com.github.stark4j.core.support.application.ApplicationManager;
import com.github.stark4j.core.support.application.DefaultApplicationManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Allen Created 2020/4/2
 */
@Configuration
@ComponentScan(basePackages = "com.github.stark4j",
        includeFilters = {@ComponentScan.Filter(MarkStrategy.class)})
public class Stark4jBoot {

    @Bean
    @ConditionalOnMissingBean(ApplicationManager.class)
    public ApplicationManager defaultApplicationManager() {
        return new DefaultApplicationManager();
    }

}
