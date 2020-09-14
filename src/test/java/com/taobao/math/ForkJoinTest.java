package com.taobao.math;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * @author huichi  shaokai.ysk@alibaba-inc.com
 * @Description:
 * @date 2020/3/31 下午7:49
 */
public class ForkJoinTest {

    @Test
    public void test() throws InterruptedException, ExecutionException, TimeoutException {

        ForkJoinPool joinPool = new ForkJoinPool(10);

        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                try {
                    forkJoinTest(joinPool);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        TimeUnit.HOURS.sleep(1);
    }

    private void forkJoinTest(ForkJoinPool joinPool) throws InterruptedException, ExecutionException, TimeoutException {
        long start = System.currentTimeMillis();
        List<Integer> taskList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        ForkJoinTask<Integer> joinTask = joinPool.submit(new RecursiveTask<Integer>() {
            @Override
            protected Integer compute() {
                long start = System.currentTimeMillis();
                List<ForkJoinTask<Integer>> joinTaskList = taskList.stream().map(task -> {
                    return new RecursiveTask<Integer>() {
                        @Override
                        protected Integer compute() {
                           // System.out.println(String.format("currentThread:%s taskId:%d threadPool:%s", Thread.currentThread().getName(), task, joinPool.toString()));
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (Exception ex) {
                                //
                            }

                            return task * 2;
                        }
                    }.fork();
                }).collect(Collectors.toList());

               // System.out.println(String.format("start to join,currentThread:%s", Thread.currentThread().getName()));

                int sum = joinTaskList.stream().mapToInt(joinTask -> joinTask.join()).sum();

                //System.out.println(String.format("currentThread:%s cost:%s", Thread.currentThread().getName(), (System.currentTimeMillis() - start)));
                return sum;

            }
        });
        //System.out.println(String.format("start to get,currentThread:%s", Thread.currentThread().getName()));
        try{
            System.out.println(String.format("result:%s currentThread:%s cost:%s",joinTask.get(11, TimeUnit.SECONDS),Thread.currentThread().getName(),(System.currentTimeMillis() - start)));
        }catch (Exception ex){
            System.out.println(String.format("Timeout,currentThread:%s", Thread.currentThread().getName()));
        }

    }
}
