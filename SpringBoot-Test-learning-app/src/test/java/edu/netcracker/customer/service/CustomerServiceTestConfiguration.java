package edu.netcracker.customer.service;

import edu.netcracker.customer.repository.CustomerRepository;
import edu.netcracker.customer.service.processor.CustomerDBProcessor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author svku0919
 * @version 24.07.2020
 */
@TestConfiguration
public class CustomerServiceTestConfiguration {
    @Bean
    public CustomerService cS(CustomerRepository customerRepository, CustomerDBProcessor customerDBProcessor) {
        return new CustomerServiceImpl(customerRepository, customerDBProcessor);
    }
}
