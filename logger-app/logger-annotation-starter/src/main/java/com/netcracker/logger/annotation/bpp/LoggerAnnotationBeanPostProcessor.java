package com.netcracker.logger.annotation.bpp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.HashMap;

/**
 * @author svku0919
 * @version 19.10.2020
 */

public class LoggerAnnotationBeanPostProcessor implements BeanPostProcessor {
    private final HashMap<String, Class<?>> classHashMap = new HashMap<>();

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Slf4j.class)) {
            classHashMap.put(beanName, bean.getClass());
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        return bean;
    }
}
