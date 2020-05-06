package edu.netcracker.metrics.service;

import edu.netcracker.common.metrics.microservice.MicroserviceName;
import edu.netcracker.common.metrics.models.ErrorMetricData;
import edu.netcracker.common.metrics.models.MetricType;
import edu.netcracker.common.metrics.models.SuccessfulMetricData;
import edu.netcracker.metrics.repository.ErrorMetricRepository;
import edu.netcracker.metrics.repository.SuccessfulMetricsRepository;
import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.TimeGauge;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MetricsService {
    private final ErrorMetricRepository errorMetricRepository;
    private final SuccessfulMetricsRepository successfulMetricsRepository;
    private final AtomicLong requestLatencySuccessful = new AtomicLong(0);
    private final AtomicLong requestLatencyError = new AtomicLong(0);

    public MetricsService(ErrorMetricRepository errorMetricRepository, SuccessfulMetricsRepository successfulMetricsRepository, MeterRegistry meterRegistry) {
        this.errorMetricRepository = errorMetricRepository;
        this.successfulMetricsRepository = successfulMetricsRepository;
        final List<Tag> tags = List.of(new ImmutableTag("microserviceame", MicroserviceName.CUSTOMER_APP.name()),
                new ImmutableTag("microserviceName", MicroserviceName.ORDER_APP.name()));
        TimeGauge.builder("successful.request.latency", requestLatencySuccessful, TimeUnit.MILLISECONDS, AtomicLong::get)
                .tags(tags)
                .description("Latency of successfully completed requests to specified app")
                .register(meterRegistry);
        TimeGauge.builder("error.request.latency", requestLatencyError, TimeUnit.MILLISECONDS, AtomicLong::get)
                .tags(tags)
                .description("Latency of unsuccessfully completed requests to specified app")
                .register(meterRegistry);
    }

    public Flux<ErrorMetricData> findAllErrorMetricsByMetricType(MicroserviceName microserviceName, MetricType metricType) {
        return errorMetricRepository.findAllByMetricTypeAndMicroserviceName(metricType, microserviceName);
    }

    public Flux<SuccessfulMetricData> findAllSuccessfulMetricByMetricType(MicroserviceName microserviceName, MetricType metricType) {
        return successfulMetricsRepository.findAllByMetricTypeAndMicroserviceName(metricType, microserviceName);
    }

    public Flux<ErrorMetricData> findAllErrorMetrics(MicroserviceName microserviceName) {
        return errorMetricRepository.findAllByMicroserviceName(microserviceName);
    }

    public Flux<SuccessfulMetricData> findAllSuccessfulMetrics(MicroserviceName microserviceName) {
        return successfulMetricsRepository.findAllByMicroserviceName(microserviceName);
    }

    public Mono<ErrorMetricData> saveErrorMetric(ErrorMetricData errorMetricData) {
//        requestLatencyError.set(errorMetricData.getEndTimeOfProcess().toEpochMilli()-errorMetricData.getStartTimeOfProcess().toEpochMilli());
        return errorMetricRepository.save(errorMetricData);
    }

    public Mono<SuccessfulMetricData> saveSuccessfulMetric(SuccessfulMetricData successfulMetricData) {
//        requestLatencySuccessful.set(successfulMetricData.getEndTimeOfProcess().toEpochMilli()-successfulMetricData.getStartTimeOfProcess().toEpochMilli());
        return successfulMetricsRepository.save(successfulMetricData);
    }
}
