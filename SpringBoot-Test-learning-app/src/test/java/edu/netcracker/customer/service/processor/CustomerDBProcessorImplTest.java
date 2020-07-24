package edu.netcracker.customer.service.processor;

import edu.netcracker.customer.entity.Customer;
import edu.netcracker.customer.service.CustomerDBProcessorBaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author svku0919
 */

@ExtendWith(SpringExtension.class)
@ContextHierarchy({
        @ContextConfiguration(classes = CustomerDBProcessorBaseTest.class),
//        @ContextConfiguration(classes = DBProcessorTestConfiguration.class)
})
@ActiveProfiles("customer-service-test")
@SpringBootTest
class CustomerDBProcessorImplTest {

    @Autowired
    private CustomerDBProcessor customerDBProcessor;

    @Test
    void processCustomerToDB() {
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