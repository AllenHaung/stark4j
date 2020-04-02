package com.github.stark4j.core.support.application;

/**
 * @author Allen Created 2020/4/1
 */
public interface ApplicationManager {

    /**
     * 获取当前执行的上下文环境
     *
     * @return 返回环境信息
     */
    String getActiveProfile();

    /**
     * 是否是生产环境。默认以prod为生产环境
     *
     * @return true/false
     */
    boolean isProduce();

    /**
     * 获取应用的名称
     *
     * @return application name
     */
    String getApplicationName();

}
