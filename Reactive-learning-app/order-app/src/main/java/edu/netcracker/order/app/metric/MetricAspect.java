package edu.netcracker.order.app.metric;

import com.google.common.base.CaseFormat;
import edu.netcracker.common.metric.annotation.Metric;
import edu.netcracker.common.metric.model.MetricType;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import reactor.core.CorePublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class MetricAspect {
    private final Map<MetricType, Counter> successfulCounters;
    private final Map<MetricType, Counter> errorCounters;
    private final MeterRegistry meterRegistry;

    public MetricAspect(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.successfulCounters = new ConcurrentHashMap<>();
        this.errorCounters = new ConcurrentHashMap<>();
    }

    @SneakyThrows
    @Around("@annotation(edu.netcracker.common.metric.annotation.Metric)")
    public Object aroundInteracting(ProceedingJoinPoint joinPoint) {
        return doAround(joinPoint, ((MethodSignature) joinPoint.getSignature())
                .getMethod()
                .getAnnotation(Metric.class)
                .type());
    }

    @SneakyThrows
    private Object doAround(ProceedingJoinPoint joinPoint, MetricType metricType) {
        Object result = joinPoint.proceed();
        if (result instanceof Mono<?>) {
            return addMetricConsumer(metricType, ((Mono<?>) result), joinPoint.getSignature().getName(), false);
        } else if (result instanceof Flux<?>) {
            return addMetricConsumer(metricType, ((Flux<?>) result).collectList(), joinPoint.getSignature().getName(), true);
        }
        throw new RuntimeException("Metric annotation set on wrong method. Metric annotation must be set on Flux / Mono returning method.");
    }

    @SuppressWarnings("unchecked")
    private CorePublisher<?> addMetricConsumer(MetricType metricType, Mono<?> result, String methodName, boolean isFlux) {
        final Mono<?> mono = result
                /* on success increase success counter */
                .doOnSuccess(o -> {
                    Counter counter = successfulCounters.get(metricType);
                    if (Objects.isNull(counter)) {
                        counter = Counter.builder(convertToAnotherFormat(CaseFormat.UPPER_UNDERSCORE, CaseFormat.LOWER_UNDERSCORE, metricType.name())
                                .replaceAll("_", ".") + ".successful.event")
                                .register(meterRegistry);
                        successfulCounters.put(metricType, counter);
                    }
                    counter.increment();
                })
                /* on error increase error counter */
                .doOnError(throwable -> {
                    Counter counter = errorCounters.get(metricType);
                    if (Objects.isNull(counter)) {
                        counter = Counter.builder(convertToAnotherFormat(CaseFormat.UPPER_UNDERSCORE, CaseFormat.LOWER_UNDERSCORE, metricType.name())
                                .replaceAll("_", ".") + ".error.event")
                                .register(meterRegistry);
                        errorCounters.put(metricType, counter);
                    }
                    counter.increment();
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
