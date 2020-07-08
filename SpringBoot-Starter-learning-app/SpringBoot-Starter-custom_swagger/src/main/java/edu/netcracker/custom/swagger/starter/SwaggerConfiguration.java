package edu.netcracker.custom.swagger.starter;

import edu.netcracker.custom.swagger.starter.endpoint.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter
@EnableConfigurationProperties({SwaggerProperties.class})
public class SwaggerConfiguration {
    @Bean
    @Autowired
    @ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
    public List<Endpoint> allEndpoints(ApplicationContext applicationContext) {
        return new CustomSwagger().allEndpoints(applicationContext);
    }
}
