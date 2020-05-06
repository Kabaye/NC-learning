package edu.netcracker.common.metrics.models;

import edu.netcracker.common.metrics.microservice.MicroserviceName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@Table("error_metrics")
public class ErrorMetricData extends MetricData {
    private String errorMessage;

    @Builder
    public ErrorMetricData(Integer id, Instant startTimeOfProcess, Instant endTimeOfProcess, String errorMessage, MetricType metricType, MicroserviceName microserviceName) {
        super(id, startTimeOfProcess, endTimeOfProcess, metricType, microserviceName);
        this.errorMessage = errorMessage;
    }
}
