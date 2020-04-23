package edu.netcracker.customer.app.metrics.model;

import lombok.Data;

import java.time.Instant;

@Data
public abstract class MetricsData {
    private Instant startTimeOfProcess;
    private Instant endTimeOfProcess;
}
