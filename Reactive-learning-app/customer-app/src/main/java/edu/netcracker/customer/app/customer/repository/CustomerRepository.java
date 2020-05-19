package edu.netcracker.customer.app.customer.repository;

import edu.netcracker.customer.app.customer.entity.Customer;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {
    Mono<Customer> findByEmail(String email);

    @Query("SELECT * FROM (SELECT * FROM customers cust ORDER BY id DESC LIMIT 500) AS \"inner_customers\" ORDER BY id")
    Flux<Customer> findAll();
}
