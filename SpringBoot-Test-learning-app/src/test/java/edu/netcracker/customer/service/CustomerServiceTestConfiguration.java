package edu.netcracker.customer.service;

import edu.netcracker.customer.repository.CustomerRepository;
import edu.netcracker.customer.service.processor.CustomerDBProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author svku0919
 * @version 24.07.2020
 */
@Configuration
public class CustomerServiceTestConfiguration {
    @Bean
    public CustomerService customerService(CustomerRepository customerRepository, CustomerDBProcessor customerDBProcessor) {
        return new CustomerServiceImpl(customerRepository, customerDBProcessor);
    }
}
