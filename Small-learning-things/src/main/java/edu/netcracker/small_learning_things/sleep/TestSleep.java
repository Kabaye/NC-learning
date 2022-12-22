package edu.netcracker.small_learning_things.sleep;

/**
 * @author svku0919
 * @version 05.10.2022-15:59
 */

public class TestSleep {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            System.out.println("T1 1" + (System.currentTimeMillis() - start));
            try {
                Thread.sleep(30_000);
            } catch (InterruptedException e) {
                System.out.println("T1 2 " + (System.currentTimeMillis() - start));
                throw new RuntimeException(e);
            }
        });
        Thread t2 = new Thread(() -> {
            System.out.println("T2 1" + (System.currentTimeMillis() - start));
            try {
                Thread.sleep(5_000);
                t1.interrupt();
            } catch (InterruptedException e) {
                System.out.println("T2 2" + (System.currentTimeMillis() - start));
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();
    }
}
