package edu.netcracker.customer.service;

import edu.netcracker.customer.service.processor.CustomerDBProcessor;
import edu.netcracker.customer.service.processor.CustomerDBProcessorImpl;
import edu.netcracker.customer.utils.CustomerConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author svku0919
 * @version 24.07.2020
 */
@Configuration
@ComponentScan("edu.netcracker.customer.utils")
@EnableConfigurationProperties(CustomerConfigurationProperties.class)
public class CustomerDBProcessorCommon {

    @Bean
    public CustomerDBProcessor customerDBProcessor(CustomerConfigurationProperties customerConfigurationProperties) {
        return new CustomerDBProcessorImpl(customerConfigurationProperties);
    }
}
