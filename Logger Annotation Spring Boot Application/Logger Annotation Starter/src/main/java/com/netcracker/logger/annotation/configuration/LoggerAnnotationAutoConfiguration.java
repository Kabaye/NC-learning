package com.netcracker.logger.annotation.configuration;

import com.netcracker.logger.annotation.bpp.LoggerAnnotationBeanPostProcessor;
import com.netcracker.logger.annotation.property.LoggerAnnotationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author svku0919
 * @version 19.10.2020
 */
@Configuration
@EnableConfigurationProperties({LoggerAnnotationProperties.class})
public class LoggerAnnotationAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "auto.logging", name = "enabled", havingValue = "true")
    public LoggerAnnotationBeanPostProcessor loggerAnnotation() {
        return new LoggerAnnotationBeanPostProcessor();
    }
}
