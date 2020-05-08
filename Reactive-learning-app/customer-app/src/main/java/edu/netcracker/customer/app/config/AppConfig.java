package edu.netcracker.customer.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import edu.netcracker.common.metric.microservice.MicroserviceName;
import edu.netcracker.common.metric.serialization.InstantDeserializer;
import edu.netcracker.common.metric.serialization.InstantFormatter;
import edu.netcracker.common.metric.serialization.InstantSerializer;
import io.micrometer.core.instrument.MeterRegistry;
import org.reactivestreams.Publisher;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
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
public class AppConfig {

    public AppConfig(FormatterRegistry formatterRegistry, ObjectMapper objectMapper) {
        formatterRegistry.addFormatter(new InstantFormatter());
        objectMapper.registerModule(new SimpleModule().addDeserializer(Instant.class, new InstantDeserializer())
                .addSerializer(Instant.class, new InstantSerializer()));
    }

    @Bean
    public Docket swaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(Mono.class, Flux.class, Publisher.class)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("application", MicroserviceName.CUSTOMER_APP.name());
    }
}
