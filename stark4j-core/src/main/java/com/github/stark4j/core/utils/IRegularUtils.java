package com.github.stark4j.core.utils;

import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * 常用正则表达式
 *
 * @author Allen Created 2020/4/1
 */
public final class IRegularUtils {
    //--------------------------------------------
    //          一些常用的正则表达式
    //--------------------------------------------
    /**
     * 邮箱地址校验正则
     */
    public static final String EMAIL_REGEXP = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
    /**
     * 手机号校验正则
     */
    public static final String MOBILE_REGEXP = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
    /**
     * 是否为纯数字正则
     */
    public static final String NUMBER_REGEXP = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
    /**
     * 纯大写字母
     */
    public static final String UPPER_CASE_LETTER_REGEXP = "[A-Z]*$";
    /**
     * 纯小写字母字符串
     */
    public static final String LOWER_CASE_LETTER_REGEXP = "[a-z]*$";
    /**
     * 中国居民身份证正则表达式
     */
    public static final String CHINA_ID_CARD_REGEXP = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";

    /**
     * 判断是否是一个中国居民身份证号码
     *
     * @param idCard 身份证号码
     * @return true/false
     */
    public static boolean isChineseIdCard(String idCard) {
        return Optional.ofNullable(idCard)
                .filter(StringUtils::hasText)
                .map(var -> var.matches(CHINA_ID_CARD_REGEXP))
                .orElse(Boolean.FALSE);
    }


    /**
     * 判断一个字符串是否为纯小写字母字符串
     *
     * @param str 待检查的字符串
     * @return true/false
     */
    public static boolean isLowerCaseLetter(String str) {
        return Optional.ofNullable(str)
                .filter(StringUtils::hasText)
                .map(var -> var.matches(LOWER_CASE_LETTER_REGEXP))
                .orElse(Boolean.FALSE);
    }

    /**
     * 判断一个字符串是否为纯大写字母字符串
     *
     * @param str 待检查的字符串
     * @return true/false
     */
    public static boolean isUpperCaseLetter(String str) {
        return Optional.ofNullable(str)
                .filter(StringUtils::hasText)
                .map(var -> var.matches(UPPER_CASE_LETTER_REGEXP))
                .orElse(Boolean.FALSE);
    }

    /**
     * 判断字符串是否是一个手机号
     *
     * @param mobile 待检查的手机号
     * @return true/false
     */
    public static boolean isMobile(String mobile) {
        return Optional.ofNullable(mobile)
                .filter(StringUtils::hasText)
                .map(var -> var.matches(MOBILE_REGEXP))
                .orElse(Boolean.FALSE);
    }

    /**
     * 判断字符串是否是一个邮箱地址
     *
     * @param email 待检查的邮箱地址
     * @return true/false
     */
    public static boolean isEmail(String email) {
        return Optional.ofNullable(email)
                .filter(StringUtils::hasText)
                .map(var -> var.matches(EMAIL_REGEXP))
                .orElse(Boolean.FALSE);
    }

    /**
     * 判断字符串是否是一个纯数字字符串
     *
     * @param number 待检查的字符串
     * @return true/false
     */
    public static boolean isNumber(String number) {
        return Optional.ofNullable(number)
                .filter(StringUtils::hasText)
                .map(var -> var.matches(NUMBER_REGEXP))
                .orElse(Boolean.FALSE);
    }
}
