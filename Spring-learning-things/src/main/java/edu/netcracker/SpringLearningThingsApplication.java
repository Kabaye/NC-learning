package edu.netcracker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.netcracker.config.ComponentBean;
import edu.netcracker.config.Config;
import edu.netcracker.model.Entity;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SpringLearningThingsApplication {


    @SneakyThrows
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringLearningThingsApplication.class, args);
    }
}
