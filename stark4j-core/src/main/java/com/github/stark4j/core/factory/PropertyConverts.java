package com.github.stark4j.core.factory;

import com.github.stark4j.core.framework.PropertyConvert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用属性转换的单例实现
 *
 * @author Allen Created 2020/4/25
 */
public enum PropertyConverts {
    /**
     * 蛇形转换，将驼峰命名法转换成下划线
     */
    SNAKE(name -> {
        Matcher matcher = Pattern.compile("[A-Z]").matcher(name);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }),
    /**
     * 下划线转驼峰
     */
    LINE_TO_HUMP(name -> {
        String str = name.toLowerCase();
        Matcher matcher = Pattern.compile("_(\\w)").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    });

    private final PropertyConvert convertBean;

    PropertyConverts(PropertyConvert convertBean) {
        this.convertBean = convertBean;
    }

    public String convert(String name) {
        return convertBean.convert(name);
    }

}
