package edu.netcracker.small_learning_things.java_concurrency_in_practice.completion_service_test;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

class CallableTask implements Callable<CalcResult> {
    String taskName;
    long input1;
    int input2;

    CallableTask(String name, long v1, int v2) {
        taskName = name;
        input1 = v1;
        input2 = v2;
    }

    public CalcResult call() {
        System.out.println(" Task " + taskName + " Started -----");
        for (int i = 0; i < input2; i++) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(150) + 1);
            } catch (InterruptedException e) {
                System.out.println(" Task " + taskName + " interrupted");
                e.printStackTrace();
            }
            input1 += i;
        }
        System.out.println(" Task " + taskName + " completed");
        return new CalcResult(input1);
    }

}
