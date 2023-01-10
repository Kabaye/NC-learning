package edu.netcracker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

        ObjectMapper objectMapper = run.getBean(ObjectMapper.class);

        ObjectMapper objectMapper1 = new ObjectMapper();

        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        HashMap<String, Object> map = new HashMap<>();
        map.put("remain", 1000);
        map.put("consumption", 1000);


        String alalal = objectMapper.writeValueAsString(new TestSmth().setTest("alalal").setAdditionalParameters(map));


        String a = """
                {
                  "test" : "alalal",
                  "additionalParameters" : {
                    "remain" : 1000,
                    "consumption" : 1000
                  },
                  "alalala":"test"
                }""";


        TestSmth testSmth1 = objectMapper1.readValue(a, TestSmth.class);
        TestSmth testSmth = objectMapper.readValue(a, TestSmth.class);
        System.out.println(alalal);
        System.out.println(testSmth);
        System.out.println(testSmth1);

    }

    @Data
    @Accessors(chain = true)
    static class TestSmth {
        private String test;
        private Map<String, Object> additionalParameters;
    }
}
