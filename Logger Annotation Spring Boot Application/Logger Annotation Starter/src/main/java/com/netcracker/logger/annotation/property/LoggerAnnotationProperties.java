package com.netcracker.logger.annotation.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author svku0919
 * @version 20.10.2020
 */
@ConfigurationProperties("auto.logging")
public class LoggerAnnotationProperties {
    private boolean enabled = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
