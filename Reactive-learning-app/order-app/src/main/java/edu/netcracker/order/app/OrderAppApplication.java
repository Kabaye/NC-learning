package edu.netcracker.order.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;

@SpringBootApplication
public class OrderAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderAppApplication.class, args);
        final Random random = new Random(System.currentTimeMillis());
        Flux.interval(Duration.ofSeconds(1)).map(time -> {
            final int nextInt = random.nextInt(200_000);
            if (nextInt < 150_000) {
                return nextInt;
            }
            throw new RuntimeException("Integer > 100_000");
        }).subscribe(System.out::println,
                System.out::println);
    }

}
