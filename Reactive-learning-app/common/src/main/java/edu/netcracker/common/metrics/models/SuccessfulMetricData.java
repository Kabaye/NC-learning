package edu.netcracker.common.metrics.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("successful_metrics")
public class SuccessfulMetricData extends MetricData {
    @Builder
    public SuccessfulMetricData(Integer id, Instant startTimeOfProcess, Instant endTimeOfProcess, MetricType metricType, String microserviceId) {
        super(id, startTimeOfProcess, endTimeOfProcess, metricType, microserviceId);
    }
}
