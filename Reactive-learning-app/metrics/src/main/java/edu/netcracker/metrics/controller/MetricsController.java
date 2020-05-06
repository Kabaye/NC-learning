package edu.netcracker.metrics.controller;

import edu.netcracker.common.metrics.microservice.MicroserviceName;
import edu.netcracker.common.metrics.models.ErrorMetricData;
import edu.netcracker.common.metrics.models.MetricType;
import edu.netcracker.common.metrics.models.SuccessfulMetricData;
import edu.netcracker.metrics.service.MetricsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/metrics")
public class MetricsController {
    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/error/{microserviceName}")
    public Flux<ErrorMetricData> findErrorMetrics(@PathVariable MicroserviceName microserviceName, @RequestParam(required = false) MetricType metricType) {
        return Objects.isNull(metricType) ? metricsService.findAllErrorMetrics(microserviceName) :
                metricsService.findAllErrorMetricsByMetricType(microserviceName, metricType);
    }

    @GetMapping("/successful/{microserviceName}")
    public Flux<SuccessfulMetricData> findSuccessfulMetric(@PathVariable MicroserviceName microserviceName, @RequestParam(required = false) MetricType metricType) {
        return Objects.isNull(metricType) ? metricsService.findAllSuccessfulMetrics(microserviceName) :
                metricsService.findAllSuccessfulMetricByMetricType(microserviceName, metricType);
    }

    @PostMapping("/error")
    Mono<ErrorMetricData> saveErrorMetric(@RequestBody ErrorMetricData errorMetricData) {
        return metricsService.saveErrorMetric(errorMetricData);
    }

    @PostMapping("/successful")
    Mono<SuccessfulMetricData> saveSuccessfulMetric(@RequestBody SuccessfulMetricData successfulMetricData) {
        return metricsService.saveSuccessfulMetric(successfulMetricData);
    }
}
