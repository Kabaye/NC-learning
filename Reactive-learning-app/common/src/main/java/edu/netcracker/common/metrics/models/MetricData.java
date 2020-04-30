package edu.netcracker.common.metrics.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.netcracker.common.metrics.serialization.InstantDeserializer;
import edu.netcracker.common.metrics.serialization.InstantSerializer;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Data
public abstract class MetricData {
    @Id
    private Integer id;
    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant startTimeOfProcess;
    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
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
