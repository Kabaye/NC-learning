package edu.netcracker.customer.service;

import edu.netcracker.customer.entity.Customer;
import edu.netcracker.customer.repository.CustomerRepository;
import edu.netcracker.customer.service.processor.CustomerDBProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

/**
 * @author svku0919
 * @version 24.07.2020
 */
@ExtendWith(SpringExtension.class)
@ContextHierarchy({
        @ContextConfiguration(classes = CustomerDBProcessorBaseTest.class),
        @ContextConfiguration(classes = CustomerServiceTestConfiguration.class)
})
@ActiveProfiles("customer-service-test")
@SpringBootTest
class CustomerServiceImplTest {
    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerDBProcessor customerDBProcessor;

    @Test
    void pay() {
        final String email = "svku@nc.com";
        final double initialMoney = 895.48;
        final double payMoney = 199.99;
        final double expectedMoney = initialMoney - payMoney;

        Customer testCustomer = new Customer().setFirstName("Test 2")
                .setMoney(initialMoney)
                .setEmail(email);

        Customer expected = new Customer().setFirstName("Test 2")
                .setMoney((double) (long) (expectedMoney * 1000))
                .setEmail(email);

        Mockito.doReturn(expected).when(customerRepository).save(Mockito.any());
        final Customer of = Customer.of(testCustomer);
        of.setMoney((double) (long) (of.getMoney() * 1000));
        Mockito.doReturn(Optional.of(of)).when(customerRepository).findByEmail(Mockito.anyString());

        Customer customer = customerService.pay(email, payMoney);
        Assertions.assertEquals(expectedMoney, customer.getMoney(), 0.000001);
    }

    @Test
    void deposit() {
        final String email = "svku@nc.com";
        final double initialMoney = 895.48;
        final double depositMoney = 500.28;
        final double expectedMoney = initialMoney + depositMoney;

        Customer testCustomer = new Customer().setFirstName("Test 2")
                .setMoney(initialMoney)
                .setEmail(email);

        Customer expected = new Customer().setFirstName("Test 2")
                .setMoney((double) (long) (expectedMoney * 1000))
                .setEmail(email);

        Mockito.doReturn(expected).when(customerRepository).save(Mockito.any());
        final Customer of = Customer.of(testCustomer);
        of.setMoney((double) (long) (of.getMoney() * 1000));
        Mockito.doReturn(Optional.of(of)).when(customerRepository).findByEmail(Mockito.anyString());

        Customer customer = customerService.deposit(email, depositMoney);
        Assertions.assertEquals(expectedMoney, customer.getMoney(), 0.000001);
    }

    @Test
    void payMore() {
        final String email = "svku@nc.com";
        final double initialMoney = 152.48;
        final double payMoney = 199.99;
        final double expectedMoney = initialMoney - payMoney;

        Customer testCustomer = new Customer().setFirstName("Test 2")
                .setMoney(initialMoney)
                .setEmail(email);

        Customer expected = new Customer().setFirstName("Test 2")
                .setMoney((double) (long) (expectedMoney * 1000))
                .setEmail(email);

        Mockito.doReturn(expected).when(customerRepository).save(Mockito.any());
        final Customer of = Customer.of(testCustomer);
        of.setMoney((double) (long) (of.getMoney() * 1000));
        Mockito.doReturn(Optional.of(of)).when(customerRepository).findByEmail(Mockito.anyString());

        Assertions.assertThrows(RuntimeException.class, () -> customerService.pay(email, payMoney), "Not enough money for this operation!");
    }
}