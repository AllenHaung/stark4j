package com.github.stark4j.mybatis.register;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.ibatis.type.*;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;

import javax.annotation.PostConstruct;

/**
 * @author Allen Created 2020/4/19
 */
@Getter
@AllArgsConstructor
public class MyBatisTypeHandlerRegister implements ImportBeanDefinitionRegistrar {

    private static final String BASE_PACKAGES = "com.github.stark4j.mybatis.handler";

    public TypeHandlerRegistry typeHandlerRegistry;

    @PostConstruct
    public MyBatisTypeHandlerRegister initRegister() {
        TypeHandlerRegistry registry = getTypeHandlerRegistry();
        registry.register(InstantTypeHandler.class);
        registry.register(LocalDateTimeTypeHandler.class);
        registry.register(LocalDateTypeHandler.class);
        registry.register(LocalTimeTypeHandler.class);
        registry.register(OffsetDateTimeTypeHandler.class);
        registry.register(OffsetTimeTypeHandler.class);
        registry.register(ZonedDateTimeTypeHandler.class);
        getTypeHandlerRegistry().register(BASE_PACKAGES);
        return this;
    }

    public MyBatisTypeHandlerRegister scanBasePackages(String basePackages){
        typeHandlerRegistry.register(basePackages);
        return this;
    }

}
