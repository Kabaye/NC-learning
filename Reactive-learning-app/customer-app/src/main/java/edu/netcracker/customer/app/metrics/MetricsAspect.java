package edu.netcracker.customer.app.metrics;

import com.google.common.base.CaseFormat;
import edu.netcracker.common.metrics.microservice.MicroserviceName;
import edu.netcracker.common.metrics.models.ErrorMetricData;
import edu.netcracker.common.metrics.models.MetricType;
import edu.netcracker.common.metrics.models.SuccessfulMetricData;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reactor.core.CorePublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Aspect
public class MetricsAspect {
    private final MetricsWebClient metricsWebClient;
    private final Map<String, Timer> timers;
    private final MeterRegistry meterRegistry;

    public MetricsAspect(MetricsWebClient metricsWebClient, MeterRegistry meterRegistry) {
        this.metricsWebClient = metricsWebClient;
        this.meterRegistry = meterRegistry;
        this.timers = new ConcurrentHashMap<>();
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

    private Object doAround(ProceedingJoinPoint joinPoint, MetricType metricType) throws Throwable {
        Instant instant = Instant.now();
        Object result = joinPoint.proceed();
        if (result instanceof Mono<?>) {
            return addMetricIntermediateOperation(instant, metricType, ((Mono<?>) result), joinPoint.getSignature().getName(), false);
        } else if (result instanceof Flux<?>) {
            return addMetricIntermediateOperation(instant, metricType, ((Flux<?>) result).collectList(), joinPoint.getSignature().getName(), true);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private CorePublisher<?> addMetricIntermediateOperation(Instant instant, MetricType metricType, Mono<?> result, String methodName, boolean isFlux) {
        final Mono<?> mono = result
                /* on success send success metric */
                .doOnSuccess(o -> {
                    if (!timers.containsKey(methodName)) {
                        timers.put(methodName, Timer.builder(getFormattedMethodName(methodName))
                                .description("Custom timer for request latency").register(meterRegistry));
                    }
                    timers.get(methodName).record(Duration.between(instant, Instant.now()));

                    metricsWebClient.collectSuccessfulMetric(SuccessfulMetricData.builder()
                            .endTimeOfProcess(Instant.now())
                            .startTimeOfProcess(instant)
                            .metricType(metricType)
                            .microserviceName(MicroserviceName.CUSTOMER_APP)
                            .build())
                            .subscribe();
                })
                /* on error send error metric */
                .doOnError(throwable -> {
                    if (!timers.containsKey(methodName)) {
                        timers.put(methodName, Timer.builder(getFormattedMethodName(methodName))
                                .description("Custom timer for request latency").register(meterRegistry));
                    }
                    timers.get(methodName).record(Duration.between(instant, Instant.now()));

                    metricsWebClient.collectErrorMetric(ErrorMetricData.builder()
                            .endTimeOfProcess(Instant.now())
                            .startTimeOfProcess(instant)
                            .metricType(metricType)
                            .errorMessage(throwable.getMessage())
                            .microserviceName(MicroserviceName.CUSTOMER_APP)
                            .build())
                            .subscribe();
                })
                .name(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, methodName))
                .metrics();
        if (isFlux) {
            Mono<List<?>> listMono = (Mono<List<?>>) mono;
            return listMono.flatMapMany(Flux::fromIterable);
        }
        return mono;
    }

    private String getFormattedMethodName(String methodName) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, methodName)
                .replaceAll("_", ".");
    }
}
