package edu.netcracker.small_learning_things.java_concurrency_in_practice.shutdown_hooks;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class TestShutdownHooks {
    @SneakyThrows
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown hook");
        }));

        System.out.println("Working");
        TimeUnit.SECONDS.sleep(2);

        System.exit(100);
    }
}
