//package com.netcracker.utils;
//
//import com.netcracker.property.LoggerExceptionHandlerProperties;
//
//import java.beans.IntrospectionException;
//import java.beans.Introspector;
//import java.beans.PropertyDescriptor;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Set;
//import java.util.StringJoiner;
//import java.util.stream.Collectors;
//
///**
// * @author svku0919
// * @version 27.10.2020
// */
//
//public class ClassUtils {
//    private final LoggerExceptionHandlerProperties loggerExceptionHandlerProperties;
//
//    public ClassUtils(LoggerExceptionHandlerProperties loggerExceptionHandlerProperties) {
//        this.loggerExceptionHandlerProperties = loggerExceptionHandlerProperties;
//    }
//
//    public static String toString(Object bean, Class<?> beanClass, String beanName) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
//        StringJoiner toStringJoiner = new StringJoiner("", beanClass.getSimpleName() + " = {", "}");
//        toString(bean, beanClass, beanName, toStringJoiner);
//        return toStringJoiner.toString();
//    }
//
//    private static void toString(Object bean, Class<?> beanClass, String beanName,
//                          StringJoiner toStringJoiner) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
//        if ((isJdkClass(beanClass) | isEnumClass(beanClass) | Objects.isNull(bean)) && !isCollectionClass(beanClass) && !isMapClass(beanClass)) {
//            String bnNm = beanName + " = ";
//            if (beanName.isEmpty()) {
//                bnNm = "";
//            }
//
//            if (loggerExceptionHandlerProperties.isIncludeNull()) {
//                toStringJoiner.add(bnNm + (Objects.isNull(bean) ? "" : bean));
//            } else {
//                toStringJoiner.add(Objects.isNull(bean) ? "" : bnNm + bean);
//            }
//            return;
//        }
//
//        if (isCollectionClass(beanClass)) {
//            toStringJoiner.add(beanName + " = [");
//            final Collection<?> collection = (Collection<?>) bean;
//            int count = collection.size();
//            for (Object listItem : collection) {
//                toString(listItem, listItem.getClass(), "", toStringJoiner);
//                if (--count != 1) {
//                    toStringJoiner.add(", ");
//                }
//            }
//            toStringJoiner.add("], ");
//            return;
//        }
//
//        if (isMapClass(beanClass)) {
//            toStringJoiner.add(beanName + " = {");
//            final Set<? extends Map.Entry<?, ?>> entries = ((Map<?, ?>) bean).entrySet();
//            int count = entries.size();
//            for (Map.Entry<?, ?> mapItem : entries) {
//                final Object key = mapItem.getKey();
//                final Object value = mapItem.getValue();
//                toString(key, key.getClass(), "", toStringJoiner);
//                toString(value, value.getClass(), "", toStringJoiner);
//                if (--count != 1) {
//                    toStringJoiner.add(", ");
//                }
//            }
//            toStringJoiner.add("}, ");
//            return;
//        }
//
//        // is User Object
//        List<Method> readMethods = getReadMethods(beanClass);
//        int count = readMethods.size();
//        toStringJoiner.add("{");
//        for (Method method : readMethods) {
//            method.setAccessible(true);
//            final Object value = method.invoke(bean);
//            final String bn = method.getName()
//                    .replace("get", "")
//                    .replace("is", "");
//            toString(value, method.getReturnType(), bn.substring(0, 1).toLowerCase() + bn.substring(1), toStringJoiner);
//            if(--count != 1){
//                toStringJoiner.add(", ");
//            }
//        }
//        toStringJoiner.add("},");
//    }
//
//    public List<Method> getReadMethods(Class<?> clazz) throws IntrospectionException {
//        return Arrays.stream(Introspector.getBeanInfo(clazz, Object.class)
//                .getPropertyDescriptors())
//                .sequential()
//                .filter(propertyDescriptor -> Objects.nonNull(propertyDescriptor.getReadMethod()))
//                .map(PropertyDescriptor::getReadMethod)
//                .collect(Collectors.toList());
//    }
//
//    public static boolean isJdkClass(Class<?> clazz) {
//        return Objects.isNull(clazz.getClassLoader());
//    }
//
//    public static boolean isCollectionClass(Class<?> clazz) {
//        return Collection.class.isAssignableFrom(clazz);
//    }
//
//    public static boolean isMapClass(Class<?> clazz) {
//        return Map.class.isAssignableFrom(clazz);
//    }
//
//    public static boolean isEnumClass(Class<?> clazz) {
//        return clazz.isEnum();
//    }
//}
