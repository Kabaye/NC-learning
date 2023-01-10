package edu.netcracker.small_learning_things.java_concurrency_in_practice.for_testing_purpose;

import lombok.SneakyThrows;

public class TestSmthHere {
    @SneakyThrows
    public static void main(String[] args) {
        Thread.currentThread().getState();
    }

}
