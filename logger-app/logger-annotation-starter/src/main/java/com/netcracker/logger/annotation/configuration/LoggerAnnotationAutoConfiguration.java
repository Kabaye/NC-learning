package com.netcracker.logger.annotation.configuration;

import com.netcracker.logger.annotation.bpp.LoggerAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author svku0919
 * @version 19.10.2020
 */
@Configuration
public class LoggerAnnotationAutoConfiguration {

    @Bean
    public LoggerAnnotationBeanPostProcessor loggerAnnotation() {
        return new LoggerAnnotationBeanPostProcessor();
    }
}
