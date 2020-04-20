package edu.netcracker;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class Main {
    ReentrantLock reentrantLock2 = new ReentrantLock();

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

    private static void test4() throws InterruptedException {
        ExecutorService executorService = Executors.newWorkStealingPool();
        Random random = new Random(System.currentTimeMillis());

        List<Callable<String>> callables = Arrays.asList(
                () -> {
                    final byte[] bytes = new byte[10];
                    random.nextBytes(bytes);
                    return new String(bytes, StandardCharsets.UTF_8);
                },
                () -> {
                    final byte[] bytes = new byte[10];
                    random.nextBytes(bytes);
                    return new String(bytes, StandardCharsets.UTF_8);
                },
                () -> {
                    final byte[] bytes = new byte[10];
                    random.nextBytes(bytes);
                    return new String(bytes, StandardCharsets.UTF_8);
                }
        );
        executorService.invokeAll(callables)
                .forEach(stringFuture -> {
                    try {
                        String s = stringFuture.get();
                        System.out.println("123  " + s);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                });
    }

    private static void test5() throws InterruptedException {
        long initialTime = System.currentTimeMillis();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        Runnable task = () -> System.out.println("Scheduling 1: " + (System.currentTimeMillis() - initialTime));
        ScheduledFuture<?> future = scheduledExecutorService.schedule(task, 3, TimeUnit.SECONDS);

        TimeUnit.MILLISECONDS.sleep(1500);

        long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
        System.out.println(String.format("Remaining Delay: %dms", remainingDelay));

        Runnable task2 = () -> System.out.println("Scheduling 2: " + (System.currentTimeMillis() - initialTime));

        int initialDelay = 0;
        int period = 1;

        //не учитывает время работы процесса
        scheduledExecutorService.scheduleAtFixedRate(task2, initialDelay, period, TimeUnit.SECONDS);

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable task3 = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Scheduling 3: " + (System.currentTimeMillis() - initialTime));
            } catch (InterruptedException e) {
                System.err.println("task interrupted");
            }
        };
        //учитывает время работы процесса
        executor.scheduleWithFixedDelay(task3, 0, 1, TimeUnit.SECONDS);
    }

    ReentrantLock reentrantLock3 = new ReentrantLock();
    private int field;
    private int field2;
    private int field3;

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        new Main().test10();
    }

    private void test6() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 10_000; i++) {
            executorService.submit(this::incrementWithSync);
        }

        ConcurrencyUtils.stop(executorService);

        System.out.println("f: " + field);
    }

    private /*synchronized*/ void incrementWithSync() {
        synchronized (this) {
            field++;
        }
    }

    private void test7() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 10_000; i++) {
            executorService.submit(this::incrementWithLock);
        }

        ConcurrencyUtils.stop(executorService);

        System.out.println("f: " + field2);
    }

    private void incrementWithLock() {
        reentrantLock2.lock();
        try {
            field2++;
        } finally {
            reentrantLock2.unlock();
        }
    }

    private void test8() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            reentrantLock3.lock();
            try {
                ConcurrencyUtils.sleep(5);
            } finally {
                reentrantLock3.unlock();
            }
        });

        executor.submit(() -> {
            System.out.println("Locked: " + reentrantLock3.isLocked());
            System.out.println("Held by me: " + reentrantLock3.isHeldByCurrentThread());
            boolean locked = reentrantLock3.tryLock();
            System.out.println("Lock acquired: " + locked);
        });

        ConcurrencyUtils.stop(executor);
    }

    private void test9() {
        long startTime = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();
        ReadWriteLock lock = new ReentrantReadWriteLock();

        executor.submit(() -> {
            lock.writeLock().lock();
            try {
                ConcurrencyUtils.sleep(1);
                map.put("123", "456");
            } finally {
                lock.writeLock().unlock();
            }
        });

        Runnable readTask = () -> {
            lock.readLock().lock();
            try {
                System.out.println("Cur Thr: " + Thread.currentThread().getName() + "; curtime: " + (System.currentTimeMillis() - startTime) + "; value: " + map.get("123"));
                ConcurrencyUtils.sleep(1);
            } finally {
                lock.readLock().unlock();
            }
        };
        System.out.println("curtime start: " + (System.currentTimeMillis() - startTime));
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);

        ConcurrencyUtils.stop(executor);
    }

    private void test10() {
        long startTime = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Map<String, Integer> map = new HashMap<>();
        StampedLock stampedLock = new StampedLock();

        executorService.submit(() -> {
            long stamp = stampedLock.writeLock();
            try {
                ConcurrencyUtils.sleep(3);
                map.put("123456", 123456);
            } finally {
                stampedLock.unlockWrite(stamp);
            }
        });

        Runnable readTask = () -> {
            long stamp = stampedLock.readLock();
            try {
                System.out.println("Cur Thr: " + Thread.currentThread().getName() + "; curtime: " + (System.currentTimeMillis() - startTime) + "; value: " + map.get("123"));
                ConcurrencyUtils.sleep(2);
            } finally {
                stampedLock.unlockRead(stamp);
            }
        };

        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(readTask);

        ConcurrencyUtils.stop(executorService);
    }
}
