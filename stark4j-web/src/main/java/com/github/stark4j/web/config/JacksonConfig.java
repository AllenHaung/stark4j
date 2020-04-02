package com.github.stark4j.web.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.github.stark4j.core.utils.J8DateUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Allen Created 2020/4/1
 */
public class JacksonConfig {
    public LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(J8DateUtils.GLOBAL_DATE_TIME_FORMATTER);
    }

    public LocalDateTimeDeserializer localDateTimeDeserializer() {
        return new LocalDateTimeDeserializer(J8DateUtils.GLOBAL_DATE_TIME_FORMATTER);
    }

    public LocalTimeSerializer localTimeSerializer() {
        return new LocalTimeSerializer(J8DateUtils.GLOBAL_TIME_FORMATTER);
    }

    public LocalTimeDeserializer localTimeDeserializer() {
        return new LocalTimeDeserializer(J8DateUtils.GLOBAL_TIME_FORMATTER);
    }

    public LocalDateSerializer localDateSerializer() {
        return new LocalDateSerializer(J8DateUtils.GLOBAL_DATE_FORMATTER);
    }

    public LocalDateDeserializer localDateDeserializer() {
        return new LocalDateDeserializer(J8DateUtils.GLOBAL_DATE_FORMATTER);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder
                .serializerByType(LocalTime.class, localTimeSerializer())
                .deserializerByType(LocalTime.class, localTimeDeserializer())
                .serializerByType(LocalDate.class, localDateSerializer())
                .deserializerByType(LocalDate.class, localDateDeserializer())
                .serializerByType(LocalDateTime.class, localDateTimeSerializer())
                .deserializerByType(LocalDateTime.class, localDateTimeDeserializer());
    }
}
