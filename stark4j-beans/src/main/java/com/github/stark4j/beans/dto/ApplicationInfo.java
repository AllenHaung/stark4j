package com.github.stark4j.beans.dto;

import com.github.stark4j.beans.Stark4jVersion;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Allen Created 2020/3/30
 */
@Data
public class ApplicationInfo implements Serializable {

    private static final long serialVersionUID = Stark4jVersion.STARK4J_SERIAL_VERSION_ID;
    /**
     * 服务名称
     */
    private String application;

    /**
     * 环境
     */
    private String environment;

    /**
     * 空字符串表示
     */
    private String empty;

    /**
     * 空数组表示
     */
    private List<String> emptyArray;

    /**
     * null数组表示
     */
    private List<Object> nullArray;

    /**
     * 时间格式
     */
    private LocalDateTime dateTime;

    /**
     * 时间格式
     */
    private LocalDate date;

    /**
     * 时间格式
     */
    private LocalTime time;

    public static ApplicationInfo create(String application, String environment) {
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setApplication(application);
        applicationInfo.setEnvironment(environment);
        applicationInfo.setEmptyArray(new ArrayList<>(1));
        applicationInfo.setDateTime(LocalDateTime.now());
        applicationInfo.setDate(LocalDate.now());
        applicationInfo.setTime(LocalTime.now());
        return applicationInfo;
    }

}
