package edu.netcracker.customer.repository;

import edu.netcracker.customer.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);

    void deleteByEmail(String email);

    List<Customer> findAll();
}
