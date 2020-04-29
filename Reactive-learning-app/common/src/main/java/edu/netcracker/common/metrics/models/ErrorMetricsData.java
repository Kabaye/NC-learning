package edu.netcracker.common.metrics.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorMetricsData extends MetricData {
    private String errorMessage;

    @Builder
    public ErrorMetricsData(Integer id, Instant startTimeOfProcess, Instant endTimeOfProcess, String errorMessage) {
        super(id, startTimeOfProcess, endTimeOfProcess);
        this.errorMessage = errorMessage;
    }
}
