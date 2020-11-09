package com.netcracker.configuration;

import com.netcracker.bpp.LoggerExceptionHandlerBeanPostProcessor;
import com.netcracker.property.LoggerExceptionHandlerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author svku0919
 * @version 19.10.2020
 */
@Configuration
@EnableConfigurationProperties({LoggerExceptionHandlerProperties.class})
public class LoggerExceptionHandlerAutoConfiguration {
    private final LoggerExceptionHandlerProperties loggerExceptionHandlerProperties;

    public LoggerExceptionHandlerAutoConfiguration(LoggerExceptionHandlerProperties loggerExceptionHandlerProperties) {
        this.loggerExceptionHandlerProperties = loggerExceptionHandlerProperties;
    }

    @Bean
    @ConditionalOnProperty(prefix = "auto.logging", name = "enabled", havingValue = "true")
    public LoggerExceptionHandlerBeanPostProcessor loggerAnnotation() {
        return new LoggerExceptionHandlerBeanPostProcessor();
    }
}
