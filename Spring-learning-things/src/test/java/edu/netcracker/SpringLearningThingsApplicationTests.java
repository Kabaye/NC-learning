package edu.netcracker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.netcracker.config.ComponentBean;
import edu.netcracker.config.Config;
import edu.netcracker.model.Entity;
import edu.netcracker.model.SmallClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;

@SpringBootTest
class SpringLearningThingsApplicationTests {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void givenTwoBeansConfig_whenCallGetBeanMethod_thenConfigurationReturnSameComponentNew() {
        ComponentBean component = applicationContext.getBean(ComponentBean.class);
        Config configuration = applicationContext.getBean(Config.class);

        Entity componentEntity = component.getEntityComponent();
        Entity anotherComponentEntity = component.getEntityComponent();

        Entity configurationEntity = configuration.getEntity();
        Entity anotherConfigurationEntity = configuration.getEntity();

        Assertions.assertSame(configurationEntity, anotherConfigurationEntity);
        Assertions.assertNotSame(componentEntity, anotherComponentEntity);
    }

    @Test
    void givenTwoObjectMappers_whenCallUnmarshall_thenNewThrowExceptionFromSpringMaps() throws JsonProcessingException {
        ObjectMapper sprintObjectMapper = applicationContext.getBean(ObjectMapper.class)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ObjectMapper newObjectMapper = new ObjectMapper()
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);

        String a = """
                {
                  "test" : "alalal",
                  "additionalParameters" : {
                    "remain" : 1000,
                    "consumption" : 1000
                  },
                  "alalala":"test"
                }""";


        Assertions.assertThrows(JsonProcessingException.class,() -> newObjectMapper.readValue(a, SmallClass.class));
        Assertions.assertDoesNotThrow(() ->sprintObjectMapper.readValue(a, SmallClass.class));
    }

}
