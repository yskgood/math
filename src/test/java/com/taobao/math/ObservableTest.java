package com.taobao.math;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ObservableTest {

    @Test
    public void test() {
        long start = System.currentTimeMillis();
        List<Integer> taskList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            taskList.add(i);
        }
        List<Observable<Integer>> observableList = taskList.stream().map(task -> rxJobExecute(task)).collect(Collectors.toList());
        System.out.println("start");
        List<Integer> total = new ArrayList<>();
        Observable.merge(observableList).filter(t -> t != -1).blockingSubscribe(integer -> total.add(integer));

        System.out.println(String.format(" currentThread:%s cost:%s", Thread.currentThread().getName(), (System.currentTimeMillis() - start)));
        System.out.println(String.format("result:%s", total));
    }

    public Observable<Integer> rxJobExecute(Integer task) {
        return Observable.fromCallable(() -> {
            //System.out.println("task start");
            Observable<Integer> ob1 = Observable.fromCallable(() -> {
                TimeUnit.MILLISECONDS.sleep(1000);
                return task;
            }).subscribeOn(Schedulers.io());

            Observable<Integer> ob2 = Observable.fromCallable(() -> {
                TimeUnit.MILLISECONDS.sleep(1000);
                Random random = new Random();

                if (random.nextInt(100) % 2 == 0) {
                    throw new RuntimeException("异常了");
                }
                return task;
            }).onErrorReturn(throwable -> {
                System.out.println(String.format("错误：%s", throwable.getMessage()));
                return -1;
            }).subscribeOn(Schedulers.io());

            return Observable.zip(ob1, ob2, new BiFunction<Integer, Integer, Integer>() {

                @Override
                public Integer apply(Integer integer, Integer integer2) throws Throwable {
                    if (integer2 == -1) {
                        return -1;
                    }
                    return integer + integer2;
                }
            }).blockingSingle();

        }).subscribeOn(Schedulers.io());
    }
}
