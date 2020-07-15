package edu.netcracker.small_learning_things.java_concurrency_in_practice.not_volatile_vars_in_synchronized_block;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestSynchonizedMethodAndNotVolatileVariableChangingInside {
    private NotVolatileClassForChanging a;
    private NotVolatileClassForChanging b;

    public TestSynchonizedMethodAndNotVolatileVariableChangingInside() {
        this.a = new NotVolatileClassForChanging();
        this.b = new NotVolatileClassForChanging();

        a.setA("A");
        b.setA("B");

        a.setB(1);
        b.setB(2);

        a.setC(1.0);
        b.setC(2.0);
    }

    public static void main(String[] args) {
        TestSynchonizedMethodAndNotVolatileVariableChangingInside t = new TestSynchonizedMethodAndNotVolatileVariableChangingInside();

        ExecutorService es = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            es.submit(t::changeSmth);
        }
    }

    public synchronized void changeSmth() {
        System.out.println(Thread.currentThread().getName());
        final String a = String.valueOf(new Random().nextInt(1000));
        System.out.println("a: " + a + " old a: " + this.a.getA());
        this.a.setA(a);
        final String b = String.valueOf(new Random().nextInt(1000));
        System.out.println("b: " + b + " old b: " + this.b.getA());
        this.b.setA(b);
    }
}


