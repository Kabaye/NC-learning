package edu.netcracker.customer.app.metrics;

import com.google.common.base.CaseFormat;
import edu.netcracker.common.metrics.microservice.MicroserviceName;
import edu.netcracker.common.metrics.models.ErrorMetricData;
import edu.netcracker.common.metrics.models.MetricType;
import edu.netcracker.common.metrics.models.SuccessfulMetricData;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
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
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Aspect
public class MetricsAspect {
    private final MetricsWebClient metricsWebClient;
    private final Map<MetricType, Counter> successfulCounters;
    private final Map<MetricType, Counter> errorCounters;
    private final MeterRegistry meterRegistry;

    public MetricsAspect(MetricsWebClient metricsWebClient, MeterRegistry meterRegistry) {
        this.metricsWebClient = metricsWebClient;
        this.meterRegistry = meterRegistry;
        this.successfulCounters = new ConcurrentHashMap<>();
        this.errorCounters = new ConcurrentHashMap<>();
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
            return addMetricConsumer(instant, metricType, ((Mono<?>) result), joinPoint.getSignature().getName(), false);
        } else if (result instanceof Flux<?>) {
            return addMetricConsumer(instant, metricType, ((Flux<?>) result).collectList(), joinPoint.getSignature().getName(), true);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private CorePublisher<?> addMetricConsumer(Instant instant, MetricType metricType, Mono<?> result, String methodName, boolean isFlux) {
        final Mono<?> mono = result
                /* on success send success metric */
                .doOnSuccess(o -> {
                    Counter counter = successfulCounters.get(metricType);
                    if (Objects.isNull(counter)) {
                        counter = Counter.builder(convertToAnotherFormat(CaseFormat.UPPER_UNDERSCORE, CaseFormat.LOWER_UNDERSCORE, metricType.name())
                                .replaceAll("_", ".") + ".successful.event")
                                .register(meterRegistry);
                        successfulCounters.put(metricType, counter);
                    }
                    counter.increment();

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
                    Counter counter = errorCounters.get(metricType);
                    if (Objects.isNull(counter)) {
                        counter = Counter.builder(convertToAnotherFormat(CaseFormat.UPPER_UNDERSCORE, CaseFormat.LOWER_UNDERSCORE, metricType.name())
                                .replaceAll("_", ".") + ".error.event")
                                .register(meterRegistry);
                        successfulCounters.put(metricType, counter);
                    }
                    counter.increment();

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

    private String convertToAnotherFormat(CaseFormat from, CaseFormat to, String st) {
        return from.to(to, st);
    }

}
