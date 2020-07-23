package edu.netcracker.customer.service;

import edu.netcracker.customer.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer);

    Customer findByEmail(String email);

    List<Customer> findAll();

    Customer updateCustomer(Customer customer);

    void deleteByEmail(String email);

    Customer pay(String email, Double amount);

    Customer deposit(String email, Double amount);
}
