package com.taobao.math;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ThreadPoolExecutorTest {

    @Test
    public void test() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                threadPoolTest(executor);
            }).start();
        }
        TimeUnit.HOURS.sleep(1);
    }

    private void threadPoolTest(ExecutorService executor) {
        List<Integer> taskList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        long start = System.currentTimeMillis();
        List<Future<Integer>> futureList = taskList.stream().map(task -> {
            return executor.submit(() -> {
                //System.out.println(String.format("currentThread:%s taskId:%d", Thread.currentThread().getName(), task));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception ex) {
                    //
                }

                return task * 2;
            });
        }).collect(Collectors.toList());

        int sum = futureList.stream().mapToInt(future -> {
            try {
                return future.get(11,TimeUnit.SECONDS);
            } catch (Exception e) {
                System.out.println(String.format("Timeout,currentThread:%s", Thread.currentThread().getName()));
            }
            return 0;
        }).sum();

        System.out.println(String.format("currentThread:%s cost:%s sum:%s", Thread.currentThread().getName(), (System.currentTimeMillis() - start), sum));
    }
}
