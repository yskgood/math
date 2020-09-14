package com.taobao.math;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class FlowableTest {

    @Test
    public void test1() {
        Flowable.just("Hello RxJava").subscribe(str -> {
            System.out.println(str);
        });
    }

    @Test
    public void fromCallable() throws InterruptedException {
        Flowable.fromCallable(() -> {
            Thread.sleep(1000); //  imitate expensive computation
            return "Done";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(System.out::println, Throwable::printStackTrace);

        Thread.sleep(2000); // <--- wait for the flow to finish
    }

    @Test
    public void jobTest() throws InterruptedException {
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                zip();
            }).start();
        }

        TimeUnit.HOURS.sleep(1);
    }

    @Test
    public void zip() {
        long start = System.currentTimeMillis();
        List<Integer> taskList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        List<Flowable<Integer>> flowableList = taskList.stream().map(task -> {
            return Flowable.fromCallable(() -> {
                TimeUnit.SECONDS.sleep(1);
                return task * 2;
            }).subscribeOn(Schedulers.io());
        }).collect(Collectors.toList());

        Integer total = (Integer) Flowable.zip(flowableList, new Function<Object[], Object>() {
            @Override
            public Object apply(Object[] objects) throws Throwable {
                Integer sum = 0;
                for (Object obj : objects) {
                    Integer task = (Integer) obj;
                    sum += task;
                }
                return sum;
            }
        }).subscribeOn(Schedulers.computation()).blockingFirst();

        System.out.println(String.format("result:%s currentThread:%s cost:%s", total, Thread.currentThread().getName(), (System.currentTimeMillis() - start)));
    }

    @Test
    public void zip2() {
        long start = System.currentTimeMillis();
        Flowable<Integer> flowable1 = Flowable.range(0, 10).map(i -> {
            TimeUnit.SECONDS.sleep(1);
            return i * 2;
        }).subscribeOn(Schedulers.io());

        Flowable<Integer> flowable2 = Flowable.range(0, 10).map(i -> {
            TimeUnit.SECONDS.sleep(1);
            return i * 2;
        }).subscribeOn(Schedulers.io());

        Object sum = Flowable.zip(flowable1, flowable2, new BiFunction<Integer, Integer, Object>() {
            @Override
            public Object apply(Integer integer, Integer integer2) throws Throwable {
                return integer + integer2;
            }
        }).blockingFirst();

        System.out.println(String.format("result:%s currentThread:%s cost:%s", sum, Thread.currentThread().getName(), (System.currentTimeMillis() - start)));
    }
}
