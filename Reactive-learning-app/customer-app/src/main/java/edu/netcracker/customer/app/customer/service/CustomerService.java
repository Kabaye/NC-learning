package edu.netcracker.customer.app.customer.service;

import edu.netcracker.customer.app.customer.entity.Customer;
import edu.netcracker.customer.app.customer.repository.CustomerRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Mono<Customer> findCustomerById(Integer id) {
        return customerRepository.findById(id);
    }

    public Flux<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Mono<Customer> saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Mono<Customer> updateCustomer(Integer id, Customer customerForUpd) {
        return customerRepository.findById(id).map(customer ->
                new Customer(customer.getId(), customerForUpd.getEmail(), customerForUpd.getName(), customerForUpd.getAddress(), customerForUpd.getCurrency()))
                .flatMap(customerRepository::save);
    }

    public Mono<Void> deleteCustomer(Integer id) {
        return customerRepository.deleteById(id);
    }

    public Mono<Customer> findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
