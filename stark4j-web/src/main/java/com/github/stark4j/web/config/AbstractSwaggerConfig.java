package com.github.stark4j.web.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

/**
 * @author Allen Created 2020/4/19
 */
@EnableSwagger2
public abstract class AbstractSwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalResponseMessage(RequestMethod.GET, responseMessages())
                .globalResponseMessage(RequestMethod.POST, responseMessages())
                .tags(new Tag("Application", "系统相关API"), getTags())
                .select()
                .apis(Predicates.or(basePackage("com.github.stark4j.web.interceptor"), basePackage(scanPackage())))
                .build();
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title())
                .description(description())
                .contact(new Contact(team(), website(), ""))
                .version("1.0.0")
                .build();
    }

    private Tag[] getTags() {
        List<Tag> list = new ArrayList<>();
        getApiTag().forEach((name, value) -> list.add(new Tag((String) name, (String) value)));
        Tag[] array = new Tag[list.size()];
        return list.toArray(array);
    }

    public List<ResponseMessage> responseMessages() {
        return Arrays.asList(
                createResponseMessage(302, "重定向"),
                createResponseMessage(401, "认证失败"),
                createResponseMessage(403, "禁止访问"),
                createResponseMessage(404, "无法找到被请求的资源"),
                createResponseMessage(405, "请求中指定的方法不被允许"),
                createResponseMessage(408, "请求超出了服务器的等待时间"),
                createResponseMessage(415, "媒介类型不被支持"),
                createResponseMessage(500, "系统服务异常"),
                createResponseMessage(501, "未实现的接口"),
                createResponseMessage(502, "网关请求失败"),
                createResponseMessage(503, "服务未就绪"),
                createResponseMessage(504, "网关请求超时"),
                createResponseMessage(511, "网络认证失败")
        );
    }

    private ResponseMessage createResponseMessage(int code, String message) {
        return new ResponseMessageBuilder().code(code).message(message).build();
    }

    protected abstract Map<Object, Object> getApiTag();

    protected abstract String title();

    protected abstract String description();

    protected abstract String scanPackage();

    protected String website(){
        return "https://github.com/AllenHaung/stark4j";
    }

    protected String team(){
        return "github stark4j.";
    }
}
