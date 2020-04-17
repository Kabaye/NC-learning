package edu.netcracker;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        test3();
    }

    private static void test1() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello 1" + threadName);
        });
        Thread.sleep(10);
        executorService.execute(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello 2" + threadName);
        });


        ExecutorService executorService2 = Executors.newSingleThreadExecutor();
        executorService2.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello 11" + threadName);
        });
        executorService2.execute(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello 22" + threadName);
        });
    }

    private static void test2() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        //задачи больше чем 5 секунд
        executorService.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello 1" + threadName);
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        });
        executorService.execute(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello 2" + threadName);
        });
        try {
            System.out.println("shutdown exservice");
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        } finally {
            if (!executorService.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            executorService.shutdownNow();
            System.out.println("shutdown finished");
        }
    }

    private static void test3() throws ExecutionException, InterruptedException, TimeoutException {
        Random random = new Random(System.currentTimeMillis());
        Callable<Integer> task = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                return random.nextInt(100);
            } catch (InterruptedException exc) {
                System.err.println("123" + exc.getMessage());
                return -1;
            }
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> futureResult = executorService.submit(task);

        System.out.println("done? " + futureResult.isDone());

        //for mistake:
        //executorService.shutdownNow();

        Integer res = futureResult.get();

        //get with timeout (with exception):
//        Integer res = futureResult.get(100, TimeUnit.MILLISECONDS);

        System.out.println("done? " + futureResult.isDone());

        System.out.println("res " + res);
    }
}
