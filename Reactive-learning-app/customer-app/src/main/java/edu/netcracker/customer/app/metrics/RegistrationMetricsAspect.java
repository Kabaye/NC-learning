package edu.netcracker.customer.app.metrics;

import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Aspect
public class RegistrationMetricsAspect {

    @Around("@annotation(edu.netcracker.common.metrics.annotations.RegistrationMetricAnnotation)")
    @SneakyThrows
    public Object aroundRegistration(ProceedingJoinPoint joinPoint) {
        Object result = joinPoint.proceed();
        if (result instanceof Mono<?>) {
            Mono<?> mono = (Mono<?>) result;
            return mono.doOnSuccess(res -> {
                System.out.println("Success!!!!!");
            }).doOnError(err -> {
                System.out.println("Error!!!!!!!!");
            });
        } else if (result instanceof Flux<?>) {
            Flux<?> flux = (Flux<?>) result;
            return flux.doFinally(signalType -> {
                //use map here for both MONO and FLUX
                System.out.println("Flux Success!!!!!");
            }).doOnError(err -> {
                System.out.println("Flux Error!!!!!!!!");
            });
        }
        return result;
    }
}
