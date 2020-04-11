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

        List<Integer> taskList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        ForkJoinPool joinPool = new ForkJoinPool();
        ForkJoinTask<Integer> joinTask = joinPool.submit(new RecursiveTask<Integer>() {
            @Override
            protected Integer compute() {
                long start = System.currentTimeMillis();
                List<ForkJoinTask<Integer>> joinTaskList = taskList.stream().map(task -> {
                    return new RecursiveTask<Integer>() {
                        @Override
                        protected Integer compute() {
                            System.out.println(String.format("currentThread:%s taskId:%d", Thread.currentThread().getName(), task));
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (Exception ex) {
                                //
                            }

                            return task * 2;
                        }
                    }.fork();
                }).collect(Collectors.toList());
                int sum = joinTaskList.stream().mapToInt(joinTask -> joinTask.join()).sum();

                System.out.println(String.format("currentThread:%s cost:%s", Thread.currentThread().getName(), (System.currentTimeMillis() - start)));
                return sum;

            }
        });

        System.out.println(joinTask.get(3000, TimeUnit.SECONDS));

    }
}
