package edu.netcracker.customer.app.metrics;

import edu.netcracker.common.metrics.models.ErrorMetricData;
import edu.netcracker.common.metrics.models.MetricType;
import edu.netcracker.common.metrics.models.SuccessfulMetricData;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reactor.core.CorePublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;

@Component
@Aspect
public class MetricsAspect {
    private static final String microserviceId = "e3df1ac2-5a0c-4592-87c2-639ba94afb";

    private final MetricsWebClient metricsWebClient;

    public MetricsAspect(MetricsWebClient metricsWebClient) {
        this.metricsWebClient = metricsWebClient;
    }

    @SneakyThrows
    @Around("@annotation(edu.netcracker.common.metrics.annotations.RegistrationMetricAnnotation)")
    public Object aroundRegistration(ProceedingJoinPoint joinPoint) {
        return doAround(joinPoint, MetricType.REGISTRATION);
    }

    @SneakyThrows
    @Around("@annotation(edu.netcracker.common.metrics.annotations.InteractingMetricAnnotation)")
    public Object aroundInteracting(ProceedingJoinPoint joinPoint) {
        return doAround(joinPoint, MetricType.INTERACTING);
    }

    @SneakyThrows
    @Around("@annotation(edu.netcracker.common.metrics.annotations.DeletingMetricAnnotation)")
    public Object aroundDeleting(ProceedingJoinPoint joinPoint) {
        return doAround(joinPoint, MetricType.DELETING);
    }

    private Object doAround(ProceedingJoinPoint joinPoint, MetricType registration) throws Throwable {
        Instant instant = Instant.now();
        Object result = joinPoint.proceed();
        if (result instanceof Mono<?>) {
            return addMetricIntermediateOperation(instant, registration, ((Mono<?>) result), false);
        } else if (result instanceof Flux<?>) {
            return addMetricIntermediateOperation(instant, registration, ((Flux<?>) result).collectList(), true);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private CorePublisher<?> addMetricIntermediateOperation(Instant instant, MetricType metricType, Mono<?> result, boolean isFlux) {
        final Mono<?> mono = result
                /* on success send success metric */
                .doOnSuccess(o -> metricsWebClient.collectSuccessfulMetric(SuccessfulMetricData.builder()
                        .endTimeOfProcess(Instant.now())
                        .startTimeOfProcess(instant)
                        .metricType(metricType)
                        .microserviceId(microserviceId)
                        .build())
                        .subscribe())
                /* on error send error metric */
                .doOnError(throwable -> metricsWebClient.collectErrorMetric(ErrorMetricData.builder()
                        .endTimeOfProcess(Instant.now())
                        .startTimeOfProcess(instant)
                        .metricType(metricType)
                        .errorMessage(throwable.getMessage())
                        .microserviceId(microserviceId)
                        .build())
                        .subscribe());
        if (isFlux) {
            Mono<List<?>> fluxMono = (Mono<List<?>>) mono;
            return fluxMono.flatMapMany(Flux::fromIterable);
        }
        return mono;
    }
}
