package edu.netcracker.bpp.bpp;

import edu.netcracker.bpp.bpp.annotation.Inject;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InjectAnnotationBeanPostProcessor implements BeanPostProcessor {
    private final ConfigurableListableBeanFactory configurableListableBeanFactory;
    private final HashMap<Pair<String, String>, String> beansWithInjection;
    private final List<String> dependencies;

    public InjectAnnotationBeanPostProcessor(ConfigurableListableBeanFactory configurableListableBeanFactory) {
        this.configurableListableBeanFactory = configurableListableBeanFactory;
        beansWithInjection = new HashMap<>();
        dependencies = new ArrayList<>();
    }

    //    constructor injection
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        for (Field field : beanClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                beansWithInjection.putIfAbsent(Pair.of(beanClass.getPackageName(), field.getName()), beanName);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        for (Field field : beanClass.getDeclaredFields()) {
            beansWithInjection.computeIfPresent(Pair.of(beanClass.getPackageName(), field.getName()), (pair, bName) -> {
                if (bName.equals(beanName)) {
                    ReflectionUtils.makeAccessible(field);
                    Object valueToInject = findOrCreate(field.getType(), field.getName());
                    ReflectionUtils.setField(field, bean, valueToInject);
                }
                return bName;
            });
            dependencies.clear();
        }
        return bean;
    }

    @SneakyThrows
    private Object findOrCreate(Class<?> clazz, String name) {
        if (dependencies.contains(name)) {
            throw new RuntimeException("There are some unresolved circular dependencies");
        }
        if (configurableListableBeanFactory.containsBean(name)) {
            return configurableListableBeanFactory.getBean(name);
        } else {
            dependencies.add(name);
            Constructor<?> constructor = clazz.getConstructors()[0];
            Parameter[] parameters = constructor.getParameters();
            Object[] parameterValues = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                parameterValues[i] = findOrCreate(parameters[i].getType(), parameters[i].getName());
            }
            Object newBean = constructor.newInstance(parameterValues);
            configurableListableBeanFactory.registerSingleton(name, newBean);
            return newBean;
        }
    }
}
