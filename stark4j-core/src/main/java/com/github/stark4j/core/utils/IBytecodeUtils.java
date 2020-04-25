package com.github.stark4j.core.utils;

import javassist.*;
import javassist.bytecode.*;
import javassist.bytecode.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.function.Function;

/**
 * @author Allen Created 2020/4/19
 */
public final class IBytecodeUtils {

    private static final ClassPool CLASS_POOL = ClassPool.getDefault();

    static {
        ClassClassPath classPath = new ClassClassPath(IBytecodeUtils.class);
        CLASS_POOL.insertClassPath(classPath);
    }

    /**
     * 通过className创建Class
     *
     * @param className classname
     * @return CtClass
     */
    public static CtClass makeClass(String className) {
        ClassPool classPool = ClassPool.getDefault();
        return classPool.makeClass(className);
    }

    /**
     * 将一个classFile加载到jvm
     *
     * @param classFile classFile
     * @return 返回class对象
     */
    public static Class<?> loadClass(ClassFile classFile) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            classFile.write(dataOutputStream);
            return IRuntimeUtils.ClassDefiner.defineClass(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取某个方法的所有的参数名称
     *
     * @param method javassist method
     * @return 返回参数名称数组
     * @throws NotFoundException NotFoundException
     */
    public static String[] argsNames(CtMethod method) throws NotFoundException {
        MethodInfo methodInfo = method.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        String[] variableNames = new String[method.getParameterTypes().length];
        int staticIndex = Modifier.isStatic(method.getModifiers()) ? 0 : 1;
        for (int i = 0; i < variableNames.length; i++) {
            variableNames[i] = attr.variableName(i + staticIndex);
        }
        return variableNames;
    }

    public static CtClass[] fromClasses(Class<?>... classes) throws NotFoundException {
        if (classes.length > 0) {
            CtClass[] ctClasses = new CtClass[classes.length];
            for (int i = 0; i < classes.length; i++) {
                ctClasses[i] = fromClass(classes[i]);
            }
            return ctClasses;
        }
        return null;
    }

    public static CtClass fromClass(Class<?> clazz) throws NotFoundException {
        return CLASS_POOL.get(clazz.getName());
    }

    public static AnnotationsAttribute newAnnotationsAttribute(ConstPool constpool) {
        return new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
    }

    public static Annotation newAnnotation(Class<?> clazz, ConstPool constpool) {
        return new Annotation(clazz.getName(), constpool);
    }

    public static Annotation newAnnotation(Class<?> clazz, ConstPool constpool, String name, Object value) {
        Annotation annotation = newAnnotation(clazz, constpool);
        if (value instanceof String) {
            annotation.addMemberValue(name, createStringMemberValue((String) value, constpool));
        }
        return annotation;
    }

    public static Annotation newAnnotation(Class<?> clazz, ConstPool constpool, String name, Function<ConstPool, MemberValue> function) {
        Annotation annotation = newAnnotation(clazz, constpool);
        annotation.addMemberValue(name, function.apply(constpool));
        return annotation;
    }

    public static StringMemberValue createStringMemberValue(String value, ConstPool constpool) {
        return new StringMemberValue(value, constpool);
    }

    public static ClassMemberValue createClassMemberValue(String clazz, ConstPool constpool) {
        return new ClassMemberValue(clazz, constpool);
    }

    public static EnumMemberValue createEnumMemberValue(Enum enumBean, ConstPool constpool) {
        EnumMemberValue enumMemberValue = new EnumMemberValue(constpool);
        enumMemberValue.setType(enumBean.getClass().getName());
        enumMemberValue.setValue(enumBean.name());
        return enumMemberValue;
    }

    public static ArrayMemberValue createArrayMemberValue(ConstPool constpool, MemberValue... memberValues) {
        ArrayMemberValue arrayMemberValue = new ArrayMemberValue(constpool);
        arrayMemberValue.setValue(memberValues);
        return arrayMemberValue;
    }
}
