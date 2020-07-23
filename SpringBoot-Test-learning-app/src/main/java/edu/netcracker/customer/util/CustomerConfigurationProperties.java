package edu.netcracker.customer.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author svku0919
 */
@Data
@ConfigurationProperties("customer")
public class CustomerConfigurationProperties {
    private Integer moneyPrecision = 1000;
}
