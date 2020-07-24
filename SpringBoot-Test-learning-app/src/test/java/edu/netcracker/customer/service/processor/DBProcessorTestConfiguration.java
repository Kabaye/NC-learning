package edu.netcracker.customer.service.processor;

import edu.netcracker.customer.service.CustomerDBProcessorCommon;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * @author svku0919
 * @version 23.07.2020
 */

@Configuration
@Import(CustomerDBProcessorCommon.class)
public class DBProcessorTestConfiguration {
}
