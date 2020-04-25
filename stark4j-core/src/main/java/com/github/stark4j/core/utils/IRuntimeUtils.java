package com.github.stark4j.core.utils;

/**
 * @author Allen Created 2020/4/19
 */
public class IRuntimeUtils {

    /**
     * 获取CPU核心数
     *
     * @return CPU core
     */
    public static int availableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 加载字节码到jvm
     */
    public static class ClassDefiner extends ClassLoader {
        private ClassDefiner() {
        }
        public static Class<?> defineClass(byte[] code) {
            return new ClassDefiner().defineClass(null, code, 0, code.length);
        }
    }
}
