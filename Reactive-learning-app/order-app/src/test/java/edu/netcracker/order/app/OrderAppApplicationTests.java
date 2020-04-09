package edu.netcracker.order.app;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootTest
class OrderAppApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testFlux() {
        List<Integer> elements = new ArrayList<>();
        List<String> strs = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> i * 2)
                .subscribeOn(Schedulers.parallel())
                .subscribe(elements::add);
        System.out.println(elements);
    }

    @Test
    void testFlux2() {
        Flux<Integer> ints = Flux.range(1, 10);
        ints.subscribe(System.out::println);
    }

    @Test
    @SneakyThrows
    void testFlux3() {
        final Random random = new Random(System.currentTimeMillis());
        Flux.interval(Duration.ofSeconds(1)).map(time -> {
            final int nextInt = random.nextInt(200_000);
            if (nextInt < 150_000) {
                return nextInt;
            }
            throw new RuntimeException("Integer > 100_000");
        }).subscribe(System.out::println,
                System.out::println);

        Thread.sleep(1_000);
    }

    @Test
    void testFlux4() {
        Flux<String> flux = Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + 3 * i);
                    if (i == 10) sink.complete();
                    return state;
                }, (state) -> System.out.println("state: " + state));
        flux.subscribe(System.out::println);
    }
}
