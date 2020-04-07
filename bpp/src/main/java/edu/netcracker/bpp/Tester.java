package edu.netcracker.bpp;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class Tester {
    private final ApplicationContext applicationContext;
    public Tester(ApplicationContext applicationContext) {
        new ScheduledThreadPoolExecutor(1).schedule(this::testEverythingHere, 3, TimeUnit.SECONDS);
        this.applicationContext = applicationContext;
    }

    public void testEverythingHere(){
        System.out.println("Start!");
        applicationContext.getBean("testClass1");
        System.out.println("Finish!");
    }
}
