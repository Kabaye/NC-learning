package edu.netcracker.metrics.repository;

import edu.netcracker.common.metrics.models.ErrorMetricData;
import edu.netcracker.common.metrics.models.MetricType;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ErrorMetricRepository extends ReactiveCrudRepository<ErrorMetricData, Integer> {
    Flux<ErrorMetricData> findAllByMetricTypeAndMicroserviceId(MetricType metricType, String microserviceId);

    Flux<ErrorMetricData> findAllByMicroserviceId(String microserviceId);
}
