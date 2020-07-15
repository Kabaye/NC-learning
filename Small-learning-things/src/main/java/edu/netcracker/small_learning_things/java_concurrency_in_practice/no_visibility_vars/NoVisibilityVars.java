package edu.netcracker.small_learning_things.java_concurrency_in_practice.no_visibility_vars;

public class NoVisibilityVars {
    private static boolean ready;
    private static int number;

    public static void start() {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready)
                Thread.yield();
            System.out.println(number);
        }
    }
}
