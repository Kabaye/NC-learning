package edu.netcracker.customer.app.customer.controller;

import edu.netcracker.common.metric.annotation.Metric;
import edu.netcracker.customer.app.customer.entity.Customer;
import edu.netcracker.customer.app.customer.service.CustomerService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static edu.netcracker.common.metric.model.MetricType.DELETING;
import static edu.netcracker.common.metric.model.MetricType.INTERACTING;
import static edu.netcracker.common.metric.model.MetricType.REGISTRATION;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    @Metric(type = INTERACTING)
    public Mono<Customer> findCustomer(@RequestParam(required = false) Integer customerId, @RequestParam(required = false) String email) {
        return Objects.nonNull(customerId) ?
                customerService.findCustomerById(customerId) :
                customerService.findCustomerByEmail(email);
    }

    @GetMapping
    @Metric(type = INTERACTING)
    public Flux<Customer> findAllCustomers() {
        return customerService.findAllCustomers();
    }

    @PostMapping
    @Metric(type = REGISTRATION)
    public Mono<Customer> saveCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @PutMapping("/{id}")
    @Metric(type = INTERACTING)
    public Mono<Customer> updateCustomer(@PathVariable Integer id, @RequestBody(required = false) Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    @Metric(type = DELETING)
    public Mono<Void> deleteCustomer(@PathVariable Integer id) {
        return customerService.deleteCustomer(id);
    }
}