package edu.netcracker.customer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CustomerAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerAppApplication.class, args);
    }
}
