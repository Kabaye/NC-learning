package edu.netcracker.customer.service.processor;

import edu.netcracker.customer.utils.CustomerConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * @author svku0919
 * @version 23.07.2020
 */

@Configuration
@ComponentScan("edu.netcracker.customer.utils")
@EnableConfigurationProperties(CustomerConfigurationProperties.class)
public class DBProcessorTestConfiguration {
    @Bean
    public CustomerDBProcessor customerDBProcessor(CustomerConfigurationProperties customerConfigurationProperties) {
        return new CustomerDBProcessorImpl(customerConfigurationProperties);
    }
}
