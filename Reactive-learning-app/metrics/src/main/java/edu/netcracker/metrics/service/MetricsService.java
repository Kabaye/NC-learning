package edu.netcracker.metrics.service;

import edu.netcracker.common.metrics.models.ErrorMetricData;
import edu.netcracker.common.metrics.models.MetricType;
import edu.netcracker.common.metrics.models.SuccessfulMetricData;
import edu.netcracker.metrics.repository.ErrorMetricRepository;
import edu.netcracker.metrics.repository.SuccessfulMetricsRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MetricsService {
    private final ErrorMetricRepository errorMetricRepository;
    private final SuccessfulMetricsRepository successfulMetricsRepository;

    public MetricsService(ErrorMetricRepository errorMetricRepository, SuccessfulMetricsRepository successfulMetricsRepository) {
        this.errorMetricRepository = errorMetricRepository;
        this.successfulMetricsRepository = successfulMetricsRepository;
    }

    public Flux<ErrorMetricData> findAllErrorMetricsByMetricType(String microserviceId, MetricType metricType) {
        return errorMetricRepository.findAllByMetricTypeAndMicroserviceId(metricType, microserviceId);
    }

    public Flux<SuccessfulMetricData> findAllSuccessfulMetricByMetricType(String microserviceId, MetricType metricType) {
        return successfulMetricsRepository.findAllByMetricTypeAndMicroserviceId(metricType, microserviceId);
    }

    public Flux<ErrorMetricData> findAllErrorMetrics(String microserviceId) {
        return errorMetricRepository.findAllByMicroserviceId(microserviceId);
    }

    public Flux<SuccessfulMetricData> findAllSuccessfulMetrics(String microserviceId) {
        return successfulMetricsRepository.findAllByMicroserviceId(microserviceId);
    }

    public Mono<ErrorMetricData> saveErrorMetric(ErrorMetricData errorMetricData) {
        return errorMetricRepository.save(errorMetricData);
    }

    public Mono<SuccessfulMetricData> saveSuccessfulMetric(SuccessfulMetricData successfulMetricData) {
        return successfulMetricsRepository.save(successfulMetricData);
    }
}
