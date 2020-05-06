package edu.netcracker.metrics.repository;

import edu.netcracker.common.metrics.microservice.MicroserviceName;
import edu.netcracker.common.metrics.models.MetricType;
import edu.netcracker.common.metrics.models.SuccessfulMetricData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface SuccessfulMetricsRepository extends ReactiveCrudRepository<SuccessfulMetricData, Integer> {
    Flux<SuccessfulMetricData> findAllByMetricTypeAndMicroserviceName(MetricType metricType, MicroserviceName microserviceName);

    Flux<SuccessfulMetricData> findAllByMicroserviceName(MicroserviceName microserviceName);
}
