package edu.netcracker.customer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CustomerAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerAppApplication.class, args);
        Schedulers.enableMetrics();
    }
}
