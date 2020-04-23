package edu.netcracker.customer.app.metrics.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class ErrorMetricsData extends MetricsData {
    private String errorMessage;
}
