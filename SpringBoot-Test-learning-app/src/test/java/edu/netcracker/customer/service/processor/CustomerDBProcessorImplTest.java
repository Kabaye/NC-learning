package edu.netcracker.customer.service.processor;

import edu.netcracker.customer.entity.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author svku0919
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DBProcessorTestConfiguration.class)
@TestPropertySource("classpath:customer-db-processor-test.properties")
class CustomerDBProcessorImplTest {

    @Autowired
    private CustomerDBProcessor customerDBProcessor;

    @Test
    void processCustomerToDB() {
        System.out.println(customerDBProcessor.getMoneyPrecision());
        final double money = 399.99;
        Customer customer = new Customer()
                .setMoney(money)
                .setAddress("123Addr")
                .setFirstName("Kabaye")
                .setEmail("scv@tut.by");

        assertEquals(money * customerDBProcessor.getMoneyPrecision(),
                customerDBProcessor.processCustomerToDB(customer).getMoney().longValue(), 0.000001);
    }

    @Test
    void processCustomerFromDB() {
        final double money = 3999990D;
        Customer customer = new Customer()
                .setMoney(money)
                .setAddress("123Addr")
                .setFirstName("Kabaye")
                .setEmail("scv@tut.by");

        assertEquals(money / customerDBProcessor.getMoneyPrecision(),
                customerDBProcessor.processCustomerFromDB(customer).getMoney(), 0.000001);
    }
}