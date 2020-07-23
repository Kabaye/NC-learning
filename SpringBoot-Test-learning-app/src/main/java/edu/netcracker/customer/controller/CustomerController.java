package edu.netcracker.customer.controller;

import edu.netcracker.customer.entity.Customer;
import edu.netcracker.customer.service.CustomerService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author svku0919
 */

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{email}")
    public Customer findCustomerByEmail(@PathVariable String email) {
        return customerService.findByEmail(email);
    }

    @GetMapping
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @PostMapping
    public Customer saveCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @PutMapping()
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping("/{email}")
    public void deleteCustomer(@PathVariable String email) {
        customerService.deleteByEmail(email);
    }

    @PostMapping("/{email}/pay")
    public Customer pay(@PathVariable String email, @RequestParam Double amount) {
        return customerService.pay(email, amount);
    }

    @PostMapping("/{email}/deposit")
    public Customer deposit(@PathVariable String email, @RequestParam Double amount) {
        return customerService.deposit(email, amount);
    }
}
