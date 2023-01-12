package edu.netcracker.config;

import edu.netcracker.model.Entity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author svku0919
 * @version 12.01.2023-11:13
 */

@Configuration
public class Config {
    @Bean
    public Entity getEntity() {
        return new Entity();
    }


    }
