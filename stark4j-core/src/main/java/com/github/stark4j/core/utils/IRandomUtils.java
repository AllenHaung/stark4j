package com.github.stark4j.core.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @author Allen Created 2020/4/2
 */
public class IRandomUtils {

    //---------------------------
    //          uuid
    //---------------------------

    /**
     * 获取uuid，不带横杠，并全部转换成大写
     *
     * @return uuid
     */
    public static String cleanUpperCaseUuid() {
        return cleanUuid().toUpperCase();
    }

    /**
     * 获取uuid，不带横杠
     *
     * @return uuid
     */
    public static String cleanUuid() {
        String uuIdString = uuid();
        String[] uuids = uuIdString.split("-");
        StringBuilder sb = new StringBuilder();
        for (String uuid : uuids) {
            sb.append(uuid);
        }
        return sb.toString();
    }

    /**
     * 获取uuid，带横杠，且uuid的值会转换成大写
     *
     * @return uuid
     */
    public static String upperCaseUuid() {
        return uuid().toUpperCase();
    }

    /**
     * 获取uuid，带横杠
     *
     * @return uuid
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }


    //---------------------------
    //          随机
    //---------------------------

    /**
     * 获取随机纯数字字符串
     *
     * @param length 字符串长度
     * @return 返回生成的字符串
     */
    public static String getRandom(int length) {
        Random random = new Random();
        StringBuilder val = new StringBuilder();
        for (int i = 0; i < length; i++) {
            val.append(random.nextInt(10));
        }
        return val.toString();
    }

    /**
     * 获取指定长度的随机字符串，包涵字母和数字
     *
     * @param length 字符串的长度
     * @return 返回生成的随机字符串
     */
    public static String getCharRandom(Integer length) {
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            boolean b = random.nextBoolean();
            if (b) {
                sb.append((char) (65 + random.nextInt(26)));
            } else {
                sb.append(random.nextInt(10));
            }
        }
        return sb.toString();
    }

}
