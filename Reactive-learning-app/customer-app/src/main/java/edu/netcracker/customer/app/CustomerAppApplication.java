package edu.netcracker.customer.app;

import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Getter
public class CustomerAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerAppApplication.class, args);
    }

}
