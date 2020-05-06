package edu.netcracker.common.metrics.models;

import edu.netcracker.common.metrics.microservice.MicroserviceName;
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
    private MicroserviceName microserviceName;

    public MetricData(Integer id, Instant startTimeOfProcess, Instant endTimeOfProcess, MetricType metricType, MicroserviceName microserviceName) {
        this.id = id;
        this.startTimeOfProcess = startTimeOfProcess;
        this.endTimeOfProcess = endTimeOfProcess;
        this.metricType = metricType;
        this.microserviceName = microserviceName;
    }
}
