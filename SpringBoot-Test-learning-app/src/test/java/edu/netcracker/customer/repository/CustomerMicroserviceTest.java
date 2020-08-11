package edu.netcracker.customer.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author svku0919
 * @version 24.07.2020
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomerMicroserviceTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextLoads() {
        assertNotNull(applicationContext);
    }

}