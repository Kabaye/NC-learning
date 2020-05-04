package edu.netcracker.common.metrics.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Data
public abstract class MetricData {
    @Id
    private Integer id;
    private Instant startTimeOfProcess;
    private Instant endTimeOfProcess;
    private MetricType metricType;
    private String microserviceId;

    public MetricData(Integer id, Instant startTimeOfProcess, Instant endTimeOfProcess, MetricType metricType, String microserviceId) {
        this.id = id;
        this.startTimeOfProcess = startTimeOfProcess;
        this.endTimeOfProcess = endTimeOfProcess;
        this.metricType = metricType;
        this.microserviceId = microserviceId;
    }
}
