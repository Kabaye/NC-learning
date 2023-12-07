package edu.netcracker;

import edu.netcracker.service.DefaultUserService;
import edu.netcracker.service.UserService;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringLearningThingsApplication {

    @SneakyThrows
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringLearningThingsApplication.class, args);
    }
}
