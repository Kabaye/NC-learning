package edu.netcracker.metrics.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import edu.netcracker.common.metrics.serialization.InstantDeserializer;
import edu.netcracker.common.metrics.serialization.InstantFormatter;
import edu.netcracker.common.metrics.serialization.InstantSerializer;
import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

import java.time.Instant;

@EnableSwagger2WebFlux
@Configuration
public class WebConfig {

    public WebConfig(FormatterRegistry formatterRegistry, ObjectMapper objectMapper) {
        formatterRegistry.addFormatter(new InstantFormatter());
        objectMapper.registerModule(new SimpleModule().addSerializer(Instant.class, new InstantSerializer())
                .addDeserializer(Instant.class, new InstantDeserializer()));
    }

    @Bean
    public Docket swaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(Mono.class, Flux.class, Publisher.class)
                .directModelSubstitute(Instant.class, Long.class)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
