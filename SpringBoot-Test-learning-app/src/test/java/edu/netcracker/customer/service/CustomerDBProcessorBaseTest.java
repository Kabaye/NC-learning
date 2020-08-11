package edu.netcracker.customer.service;

import edu.netcracker.customer.service.processor.CustomerDBProcessor;
import edu.netcracker.customer.service.processor.CustomerDBProcessorImpl;
import edu.netcracker.customer.utils.CustomerConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * @author svku0919
 * @version 24.07.2020
 */
@TestConfiguration
//@EnableConfigurationProperties(CustomerConfigurationProperties.class)
public class CustomerDBProcessorBaseTest {

    @PostConstruct
    public void init() {
        System.out.println("Base conf initialized");
    }

    @Bean
    public CustomerDBProcessor customerDBProcessor(CustomerConfigurationProperties customerConfigurationProperties) {
        return new CustomerDBProcessorImpl(customerConfigurationProperties);
    }
}
