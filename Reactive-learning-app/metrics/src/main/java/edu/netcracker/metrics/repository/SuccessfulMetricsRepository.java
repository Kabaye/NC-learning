package edu.netcracker.metrics.repository;

import edu.netcracker.common.metrics.models.MetricType;
import edu.netcracker.common.metrics.models.SuccessfulMetricData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface SuccessfulMetricsRepository extends ReactiveCrudRepository<SuccessfulMetricData, Integer> {
    Flux<SuccessfulMetricData> findAllByMetricTypeAndMicroserviceId(MetricType metricType, String microserviceId);

    Flux<SuccessfulMetricData> findAllByMicroserviceId(String microserviceId);
}
