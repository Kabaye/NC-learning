package edu.netcracker.customer.app.metrics;

import edu.netcracker.common.metrics.models.ErrorMetricData;
import edu.netcracker.common.metrics.models.MetricData;
import edu.netcracker.common.metrics.models.SuccessfulMetricData;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
class MetricsWebClient {
    private final WebClient metricWebClient;

    MetricsWebClient(WebClient.Builder webClientBuilder) {
        this.metricWebClient = webClientBuilder.baseUrl("http://localhost:8665/api/v1/metrics")
                .build();
    }

    public Mono<Void> collectSuccessfulMetric(SuccessfulMetricData successfulMetricData) {
        return sendMetric("/successful", successfulMetricData);
    }

    private Mono<Void> sendMetric(String path, MetricData metricData) {
        return metricWebClient.post()
                .uri(UriComponentsBuilder.newInstance()
                        .path(path)
                        .toUriString())
                .bodyValue(metricData)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(), clientResponse -> {
                    System.out.println(path + " metric collecting problem");
                    return clientResponse.bodyToMono(Exception.class);
                }).toBodilessEntity()
                .then(Mono.empty());
    }

    public Mono<Void> collectErrorMetric(ErrorMetricData errorMetricData) {
        return sendMetric("/error", errorMetricData);
    }
}
