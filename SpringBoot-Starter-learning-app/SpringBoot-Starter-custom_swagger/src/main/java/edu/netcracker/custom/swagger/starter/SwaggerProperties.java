package edu.netcracker.custom.swagger.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("swagger")
public class SwaggerProperties {
    private boolean enable = false;
}
