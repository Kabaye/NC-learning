package edu.netcracker.config;

import edu.netcracker.model.Entity;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author svku0919
 * @version 12.01.2023-11:16
 */

@Component
public class ComponentBean {
    @Bean
    public Entity getEntityComponent() {
        return new Entity();
    }
}
