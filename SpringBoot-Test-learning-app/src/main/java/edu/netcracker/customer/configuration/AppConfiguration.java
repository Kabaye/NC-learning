package edu.netcracker.customer.configuration;

import edu.netcracker.customer.util.CustomerConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author svku0919
 */
@Configuration
@EnableConfigurationProperties({CustomerConfigurationProperties.class})
public class AppConfiguration {
}
