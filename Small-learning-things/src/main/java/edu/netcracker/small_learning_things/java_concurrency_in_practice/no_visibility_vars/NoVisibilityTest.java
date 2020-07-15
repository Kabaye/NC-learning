package edu.netcracker.small_learning_things.java_concurrency_in_practice.no_visibility_vars;

public class NoVisibilityTest {
    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            NoVisibilityVars.start();
        }
    }
}
