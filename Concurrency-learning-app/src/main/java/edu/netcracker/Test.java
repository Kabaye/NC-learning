package edu.netcracker;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

import static edu.netcracker.ConcurrencyUtils.sleep;
import static edu.netcracker.ConcurrencyUtils.stop;

public class Test {
    ReentrantLock reentrantLock2 = new ReentrantLock();
    ReentrantLock reentrantLock3 = new ReentrantLock();
    private int field;
    private int field2;
    private int field3;

    static void test1() throws InterruptedException {
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

    static void test2() {
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

    static void test3() throws ExecutionException, InterruptedException, TimeoutException {
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

    static void test4() throws InterruptedException {
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

    static void test5() throws InterruptedException {
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

    void test6() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 10_000; i++) {
            executorService.submit(this::incrementWithSync);
        }

        stop(executorService);

        System.out.println("f: " + field);
    }

    /*synchronized*/ void incrementWithSync() {
        synchronized (this) {
            field++;
        }
    }

    void test7() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 10_000; i++) {
            executorService.submit(this::incrementWithLock);
        }

        stop(executorService);

        System.out.println("f: " + field2);
    }

    void incrementWithLock() {
        reentrantLock2.lock();
        try {
            field2++;
        } finally {
            reentrantLock2.unlock();
        }
    }

    void test8() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            reentrantLock3.lock();
            try {
                sleep(5);
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

        stop(executor);
    }

    void test9() {
        long startTime = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();
        ReadWriteLock lock = new ReentrantReadWriteLock();

        executor.submit(() -> {
            lock.writeLock().lock();
            try {
                sleep(1);
                map.put("123", "456");
            } finally {
                lock.writeLock().unlock();
            }
        });

        Runnable readTask = () -> {
            lock.readLock().lock();
            try {
                System.out.println("Cur Thr: " + Thread.currentThread().getName() + "; curtime: " + (System.currentTimeMillis() - startTime) + "; value: " + map.get("123"));
                sleep(1);
            } finally {
                lock.readLock().unlock();
            }
        };
        System.out.println("curtime start: " + (System.currentTimeMillis() - startTime));
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);

        stop(executor);
    }

    void test10() {
        long startTime = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Map<String, Integer> map = new HashMap<>();
        StampedLock stampedLock = new StampedLock();

        executorService.submit(() -> {
            long stamp = stampedLock.writeLock();
            try {
                sleep(3);
                map.put("123456", 123456);
            } finally {
                stampedLock.unlockWrite(stamp);
            }
        });

        Runnable readTask = () -> {
            long stamp = stampedLock.readLock();
            try {
                System.out.println("Cur Thr: " + Thread.currentThread().getName() + "; curtime: " + (System.currentTimeMillis() - startTime) + "; value: " + map.get("123"));
                sleep(2);
            } finally {
                stampedLock.unlockRead(stamp);
            }
        };

        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(readTask);

        stop(executorService);
    }

    void test11() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        StampedLock stampedLock = new StampedLock();

        executorService.submit(() -> {
            long stamp = stampedLock.tryOptimisticRead();
            try {
                System.out.println("validity: " + stampedLock.validate(stamp));
                sleep(3);
                System.out.println("validity: " + stampedLock.validate(stamp));
                sleep(2);
                System.out.println("validity: " + stampedLock.validate(stamp));
            } finally {
                stampedLock.unlock(stamp);
            }
        });

        executorService.submit(() -> {
            sleep(1);
            long stamp = stampedLock.writeLock();
            try {
                System.out.println("write lock");
                sleep(2);
            } finally {
                stampedLock.unlock(stamp);
                System.out.println("writing end!");
            }
        });
    }

    void test12() {
        long curTime = System.currentTimeMillis();

        var ref = new Object() {
            int count = 0;
        };

        ExecutorService executorService = Executors.newWorkStealingPool();
        StampedLock stampedLock = new StampedLock();

        executorService.submit(() -> {
            System.out.println("read st");
            System.out.println("curTime: " + (System.currentTimeMillis() - curTime));
            sleep(1);
            System.out.println("curTime2: " + (System.currentTimeMillis() - curTime));
            long stamp = stampedLock.tryOptimisticRead();
            System.out.println("curTime3: " + (System.currentTimeMillis() - curTime));
            try {
                if (ref.count == 0) {
                    stamp = stampedLock.tryConvertToWriteLock(stamp);
                    System.out.println("stamp: " + stamp);
                    sleep(1);
                    stamp = stampedLock.tryConvertToWriteLock(stamp);
                    System.out.println("stamp: " + stamp);
                    if (stamp == 0L) {
                        System.out.println("Could not convert to write stampedLock");
                        stamp = stampedLock.writeLock();
                    }
                    ref.count = 23;
                }
                System.out.println(ref.count);
            } finally {
                stampedLock.unlock(stamp);
            }
        });

        executorService.submit(() -> {
            long stamp = stampedLock.writeLock();
            try {
                System.out.println("wr start");
                sleep(10);
                System.out.println("wr end");
            } finally {
                stampedLock.unlock(stamp);
            }
        });

        stop(executorService);
    }

    void test13() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Semaphore semaphore = new Semaphore(5);

        Runnable longTask = () -> {
            boolean permission = false;
            try {
                permission = semaphore.tryAcquire(1000, TimeUnit.MILLISECONDS);
                if (permission) {
                    System.out.println("sem acquired");
                    sleep(5);
                } else {
                    System.out.println("problem with acquiring!");
                }
            } catch (InterruptedException exc) {
                throw new RuntimeException(exc.getMessage());
            } finally {
                if (permission) {
                    semaphore.release();
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            executorService.submit(longTask);
        }

        stop(executorService);
    }

    void test14() {
        AtomicInteger atomicInt = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 100_000)
                .forEach(i -> executor.submit(atomicInt::incrementAndGet));

        stop(executor);

        System.out.println(atomicInt.get());
    }

    void test15() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("foo", "bar");
        map.put("han", "solo");
        map.put("r2", "d2");
        map.put("c3", "p0");

        System.out.println(ForkJoinPool.getCommonPoolParallelism());

        String result = map.reduce(1,
                (key, value) -> {
                    System.out.println("Transform: " + Thread.currentThread().getName());
                    return key + "=" + value;
                },
                (s1, s2) -> {
                    System.out.println("Reduce: " + Thread.currentThread().getName());
                    return s1 + ", " + s2;
                });

        System.out.println("Result: " + result);
        System.out.println("Result: " + result);
    }
}
