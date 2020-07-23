package edu.netcracker.customer.service;

import edu.netcracker.customer.entity.Customer;
import edu.netcracker.customer.repository.CustomerRepository;
import edu.netcracker.customer.service.processor.CustomerDBProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerDBProcessor customerDBProcessor;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerDBProcessor customerDBProcessor) {
        this.customerRepository = customerRepository;
        this.customerDBProcessor = customerDBProcessor;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customerDBProcessor.processCustomerToDB(customer));
    }

    @Override
    public Customer findByEmail(String email) {
        return customerDBProcessor.processCustomerFromDB(customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("There is no customer with email: " + email)));
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerDBProcessor::processCustomerFromDB)
                .collect(Collectors.toList());
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer customerForUpd = findByEmail(customer.getEmail());
        customerForUpd.setAddress(customer.getAddress());
        customerForUpd.setFirstName(customer.getFirstName());
        return customerDBProcessor.processCustomerFromDB(customerRepository.save(customerForUpd));
    }

    @Override
    public void deleteByEmail(String email) {
        customerRepository.deleteByEmail(email);
    }

    @Override
    public Customer pay(String email, Double amount) {
        Customer customerForUpdate = findByEmail(email);
        final long l = (long) (amount * 1000);
        if (customerForUpdate.getMoney().longValue() < l) {
            throw new RuntimeException("Not enough money for this operation!");
        }
        return customerDBProcessor.processCustomerFromDB(
                saveCustomer(
                        customerForUpdate.setMoney((double) (customerForUpdate.getMoney().longValue() - l))));
    }

    @Override
    public Customer deposit(String email, Double amount) {
        Customer customerForUpdate = findByEmail(email);
        final long l = (long) (amount * 1000);
        return customerDBProcessor.processCustomerFromDB(
                saveCustomer(
                        customerForUpdate.setMoney((double) (customerForUpdate.getMoney().longValue() + l))));
    }
}
