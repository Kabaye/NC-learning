package edu.netcracker.common.metric.annotation;

import edu.netcracker.common.metric.model.MetricType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Metric {
    MetricType type() default MetricType.INTERACTING;
}
