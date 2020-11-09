package com.netcracker.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author svku0919
 * @version 20.10.2020
 */
@ConfigurationProperties(prefix = "le.handler")
public class LoggerExceptionHandlerProperties {
    private boolean enabled = false;
    private LoggerProperties logger = new LoggerProperties();
    private ExceptionProperties exception = new ExceptionProperties();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    static class LoggerProperties {
        private boolean includeNull = false;
        private boolean enable = false;

        public boolean isIncludeNull() {
            return includeNull;
        }

        public void setIncludeNull(boolean includeNull) {
            this.includeNull = includeNull;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }

    static class ExceptionProperties {
        private boolean enable = false;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }
}
