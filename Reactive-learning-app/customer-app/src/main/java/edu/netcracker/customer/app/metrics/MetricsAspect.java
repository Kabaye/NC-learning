package edu.netcracker.customer.app.metrics;

import edu.netcracker.common.metrics.models.ErrorMetricData;
import edu.netcracker.common.metrics.models.MetricType;
import edu.netcracker.common.metrics.models.SuccessfulMetricData;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
@Aspect
public class MetricsAspect {
    private static final String microserviceId = "e3df1ac2-5a0c-4592-87c2-639ba94afb";

    private final MetricsWebClient metricsWebClient;

    public MetricsAspect(MetricsWebClient metricsWebClient) {
        this.metricsWebClient = metricsWebClient;
    }

    @Around("@annotation(edu.netcracker.common.metrics.annotations.RegistrationMetricAnnotation)")
    @SneakyThrows
    public Object aroundRegistration(ProceedingJoinPoint joinPoint) {
        Instant instant = Instant.now();
        Object result = joinPoint.proceed();
        if (result instanceof Mono<?>) {
            return addMetricIntermediateOperation(instant, MetricType.REGISTRATION, ((Mono<?>) result));
        } else if (result instanceof Flux<?>) {
            return addMetricIntermediateOperation(instant, MetricType.REGISTRATION, ((Flux<?>) result).collectList());
        }
        return result;
    }

    @Around("@annotation(edu.netcracker.common.metrics.annotations.DeletingMetricAnnotation)")
    @SneakyThrows
    public Object aroundDeleting(ProceedingJoinPoint joinPoint) {
        Instant instant = Instant.now();
        Object result = joinPoint.proceed();
        if (result instanceof Mono<?>) {
            return addMetricIntermediateOperation(instant, MetricType.DELETING, ((Mono<?>) result));
        } else if (result instanceof Flux<?>) {
            return addMetricIntermediateOperation(instant, MetricType.DELETING, ((Flux<?>) result).collectList());
        }
        return result;
    }

    private Mono<?> addMetricIntermediateOperation(Instant instant, MetricType metricType, Mono<?> result) {
        return result
                /* on success send success metric */
                .flatMap(o -> {
                    System.out.println(metricType.name() + " success");
                    return metricsWebClient.collectSuccessfulMetric(SuccessfulMetricData.builder()
                            .endTimeOfProcess(Instant.now())
                            .startTimeOfProcess(instant)
                            .metricType(metricType)
                            .microserviceId(microserviceId)
                            .build())
                            .then(Mono.just(o));
                })
                /* on error send error metric */
                .onErrorResume(throwable -> {
                    System.out.println(metricType.name() + " error");
                    return metricsWebClient.collectErrorMetric(ErrorMetricData.builder()
                            .endTimeOfProcess(Instant.now())
                            .startTimeOfProcess(instant)
                            .metricType(metricType)
                            .errorMessage(throwable.getMessage())
                            .microserviceId(microserviceId)
                            .build())
                            .then(Mono.error(throwable));
                });
    }
}
