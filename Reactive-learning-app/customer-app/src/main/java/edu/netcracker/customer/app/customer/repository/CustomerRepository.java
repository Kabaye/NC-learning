package edu.netcracker.customer.app.customer.repository;

import edu.netcracker.customer.app.customer.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {
    Mono<Customer> findCustomerById(Integer id);

    Mono<Customer> findCustomerByEmail(String email);
}
