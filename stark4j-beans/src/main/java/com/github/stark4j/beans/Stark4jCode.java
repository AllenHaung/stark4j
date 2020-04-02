package com.github.stark4j.beans;

/**
 * @author Allen Created 2020/4/2
 */
public class Stark4jCode {
    /**
     * 处理业务成功
     */
    public static final String SUCCESS = "0000";
    /**
     * 需要做302跳转，此类请求一般携带redirect_url
     */
    public static final String REDIRECT = "302";
    /**
     * 未找到资源
     */
    public static final String NOT_FOUND = "404";
    /**
     * 系统异常
     */
    public static final String SERVER_ERROR = "500";


    //------------------
    // 异常代码约定:
    // 1-2位表示异常类型，3-6位表示服务编号，7-末尾表示异常编号.
    // SX表示系统异常，PX表示参数异常，BX表示业务逻辑异常。
    //------------------
    //  系统异常码
    //------------------
    /**
     * JAVA <-> XML 转换异常
     */
    public static final String JAXB_ERROR = "SX000000001";
    /**
     * 通过反射的方式new一个对象异常
     */
    public static final String NEW_INSTANCE_FAIL = "SX00000002";
    /**
     * 未找到构造器异常
     */
    public static final String NOT_FOUND_CONSTRUCTOR = "SX00000003";
    /**
     * 反射异常
     */
    public static final String REFLECTION_ERROR = "SX000000004";
    /**
     * PRC调用异常
     */
    public static final String RPC_INVOKE_FAIL = "SX000000005";
    /**
     * IO操作异常
     */
    public static final String IO_ERROR = "SX000000006";
    /**
     * 未找到class异常
     */
    public static final String CLASS_NOT_FOUND = "SX000000007";
    /**
     * 动态创建一个class异常
     */
    public static final String CREATE_CLASS_ERROR = "SX000000008";

    //-----------------
    //  业务异常码
    //-----------------
    /**
     * 使用策略模式时，未找到策略
     */
    public static final String NOT_FOUND_STRATEGY = "BX000000001";
    /**
     * 请求类型不支持
     */
    public static final String REQUEST_METHOD_NOT_SUPPORT = "BX000000002";
    /**
     * 数据不存在
     */
    public static final String DATA_NOT_FOUND = "BX000000003";
    /**
     * 数据已存在
     */
    public static final String DATA_EXISTED = "BX000000004";
    /**
     * 不允许的操作
     */
    public static final String NOT_ALLOW = "BX000000005";

    //-----------------
    //  参数异常码
    //-----------------
    /**
     * 参数格式错误或非法
     */
    public static final String INVALID_FORMAT = "PX000000001";
    /**
     * 参数校验失败
     */
    public static final String VALID_PARAMETER_FAIL = "PX000000002";

}
