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

    public MetricData(Integer id, Instant startTimeOfProcess, Instant endTimeOfProcess) {
        this.id = id;
        this.startTimeOfProcess = startTimeOfProcess;
        this.endTimeOfProcess = endTimeOfProcess;
    }
}
