package edu.netcracker.small_learning_things.exchanger;

import java.util.concurrent.Exchanger;

/**
 * @author svku0919
 * @version 05.10.2022-16:57
 */


public class Program {

    public static void main(String[] args) {
        Exchanger<String> ex = new Exchanger<>();

        new Thread(() -> {
            try {
                int i = 0;
                while (i < 10000) {
                    Thread.sleep(1000);
                    i +=1000;
                    System.out.println(Thread.currentThread().getName() + " slept " + i);
                }
                ex.exchange("TEST AFTER EXCHANGE!!!!!!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }, "THREAD *********").start();

        Thread thread = new Thread(() -> {
            long start = System.currentTimeMillis();
            try {
                String exchange = ex.exchange("SMTH");
                Thread.sleep(1000);
                System.out.println("Exchanged in " + (System.currentTimeMillis() - start) + ". Exchanged data = " + exchange);
            } catch (InterruptedException e) {
                System.out.println("Interrupt in " + (System.currentTimeMillis() - start));
                throw new RuntimeException(e);
            }
        }, "THREAD $$$$$$$$$$");
        thread.start();

        try {
            Thread.sleep(5000);
            thread.interrupt();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}