package edu.netcracker.common.metrics.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
public class SuccessfulMetricsData extends MetricData {
    @Builder
    public SuccessfulMetricsData(Integer id, Instant startTimeOfProcess, Instant endTimeOfProcess) {
        super(id, startTimeOfProcess, endTimeOfProcess);
    }
}
