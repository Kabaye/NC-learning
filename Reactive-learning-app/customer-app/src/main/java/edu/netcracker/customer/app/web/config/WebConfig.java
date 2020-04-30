package edu.netcracker.customer.app.web.config;

import edu.netcracker.common.metrics.serialization.InstantFormatter;
import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
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
    public Formatter<Instant> instantFormatter() {
        return new InstantFormatter();
    }
}
