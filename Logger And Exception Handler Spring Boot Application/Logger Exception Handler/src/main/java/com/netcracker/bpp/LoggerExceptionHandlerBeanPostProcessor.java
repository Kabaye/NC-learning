package com.netcracker.bpp;

import com.netcracker.annotation.AutoLogging;
import com.netcracker.utils.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * @author svku0919
 * @version 19.10.2020
 */

public class LoggerExceptionHandlerBeanPostProcessor implements BeanPostProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerExceptionHandlerBeanPostProcessor.class);
    private final HashMap<String, Pair<Class<?>, Object>> beanClassObjectMap = new HashMap<>();

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(AutoLogging.class)) {
            beanClassObjectMap.put(beanName, Pair.of(bean.getClass(), bean));
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        final Pair<Class<?>, Object> classObjectPair = beanClassObjectMap.get(beanName);
        if (classObjectPair != null) {
            Class<?> clazz = classObjectPair.getFirst();
            Object originalBean = classObjectPair.getSecond();
            if (clazz.getInterfaces().length != 0) {
                LOGGER.debug("JDK Dynamic Proxy used to enhance bean: \"" + clazz.getName() + "\".");
                return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(),
                        (proxy /*my proxy*/, method /*origin method*/, args) -> {
                            try {
                                Method m = clazz.getMethod(method.getName(), method.getParameterTypes());
                                LOGGER.info("Class \"{}\" method \"{}\" is called with args: {}.",
                                        clazz.getSimpleName(), m.getName(), args);
                                final Object value = method.invoke(bean, args);
                                LOGGER.info("Class \"{}\" method \"{}\" worked.",
                                        clazz.getSimpleName(), m.getName());
                                return value;
                            } catch (InvocationTargetException ite) {
                                LOGGER.error("Forwarding of exception, thrown by " + method.getName() + "...");
                                throw ite.getCause();
                            }
                        });
            } else {
                LOGGER.debug("SpringCGLib (Subclass) used to enhance bean: \"" + clazz.getName() + "\".");
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(clazz);
                enhancer.setInterfaces(clazz.getInterfaces());
                enhancer.setCallback((MethodInterceptor) (o /*Subclass*/, method /*origin method*/,
                                                          objects /*args*/, methodProxy /*Subclass method*/) -> {
                    LOGGER.info("Class \"{}\" method \"{}\" is called with args: {}.",
                            clazz.getSimpleName(), method.getName(), objects);
                    Object retVal = methodProxy.invokeSuper(o, objects);
                    LOGGER.info("Class \"{}\" method \"{}\" worked.",
                            clazz.getSimpleName(), method.getName());
                    return retVal;
                });
                Object proxy;

                Constructor<?>[] constructors = clazz.getConstructors();
                Class<?>[] parameterTypes = constructors[0].getParameterTypes();
                if (parameterTypes.length == 0) {
                    proxy = enhancer.create();
                } else {
                    proxy = enhancer.create(parameterTypes, new Object[parameterTypes.length]);
                }
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    Object objectToCopy = ReflectionUtils.getField(field, originalBean);
                    ReflectionUtils.setField(field, proxy, objectToCopy);
                }
                return proxy;
            }
        }
        return bean;
    }
}
