package edu.netcracker.small_learning_things.java_concurrency_in_practice.completion_service_test;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class CustomCompletionServiceTest {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        ExecutorService taskExecutor = Executors.newFixedThreadPool(3);
        CompletionService<CalcResult> taskCompletionService = new ExecutorCompletionService<>(taskExecutor);

        int submittedTasks = 5;
        for (int i = 0; i < submittedTasks; i++) {
            taskCompletionService.submit(new CallableTask(
                    getRandomString((i * 2 << 1) + 1),
                    (i + ThreadLocalRandom.current().nextInt(5) + 1) * (ThreadLocalRandom.current().nextInt(5) + 1),
                    i + ThreadLocalRandom.current().nextInt(20)
            ));
            System.out.println("Task " + i + " submitted");
        }

        for (int i = 0; i < submittedTasks; i++) {
            try {
                System.out.println("trying to take from Completion service");
                Future<CalcResult> result = taskCompletionService.take();
                System.out.println("result for a task available in queue.Trying to get()");
                // above call blocks till atleast one task is completed and results availble for it
                // but we dont have to worry which one

                // process the result here by doing result.get()
                System.out.println(result.isDone());
                CalcResult l = result.get();
                System.out.println("Task " + i + " Completed - results obtained : " + l.result);

            } catch (InterruptedException e) {
                // Something went wrong with a task submitted
                System.out.println("Error Interrupted exception");
                e.printStackTrace();
            } catch (ExecutionException e) {
                // Something went wrong with the result
                e.printStackTrace();
                System.out.println("Error get() threw exception");
            }
        }
    }

    public static String getRandomString(int size) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        return ThreadLocalRandom.current().ints(leftLimit, rightLimit + 1)
                .limit(size)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
