package edu.netcracker.customer.app.customer.service;

import com.sun.management.OperatingSystemMXBean;
import edu.netcracker.customer.app.customer.entity.Customer;
import edu.netcracker.customer.app.customer.repository.CustomerRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.management.ManagementFactory;
import java.util.regex.Pattern;

@Component
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final Pattern EMAIL_PATTERN = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");
    OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
            .getOperatingSystemMXBean();

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
//        Flux.interval(Duration.ofSeconds(3), Duration.ofMillis(5000), Schedulers.single()).subscribe(l -> printUsage());
    }

    private void printUsage() {
        //TODO 05.05.2020 WHY Cpu Usage -1 always????????
        System.out.println(bean.getProcessCpuLoad());
        System.out.println(bean.getSystemCpuLoad());
    }

    public Mono<Customer> findCustomerById(Integer id) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("No such customer with id: " + id)));
    }

    public Flux<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Mono<Customer> saveCustomer(Customer customer) {
        return Mono.just(customer)
                .map(cst -> {
                    validateEmail(customer.getEmail());
                    return cst;
                }).flatMap(customerRepository::save);
    }

    public Mono<Customer> updateCustomer(Integer id, Customer customerForUpd) {
        return Mono.just(customerForUpd)
                .map(cst -> {
                    validateEmail(customerForUpd.getEmail());
                    return cst;
                }).then(customerRepository.findById(id))
                .switchIfEmpty(Mono.error(new RuntimeException("No such customer with id: " + id)))
                .map(customer -> new Customer(customer.getId(), customerForUpd.getEmail(), customerForUpd.getName(), customerForUpd.getAddress(), customerForUpd.getCurrency()))
                .flatMap(customerRepository::save);
    }

    public Mono<Void> deleteCustomer(Integer id) {
        return customerRepository.deleteById(id);
    }

    public Mono<Customer> findCustomerByEmail(String email) {
        return Mono.just(email)
                .map(em -> {
                    validateEmail(em);
                    return em;
                }).then(customerRepository.findByEmail(email));
    }

    private void validateEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email.toLowerCase()).matches()) {
            throw new RuntimeException("Email is not correct. Please, try with correct one.");
        }
    }
}
