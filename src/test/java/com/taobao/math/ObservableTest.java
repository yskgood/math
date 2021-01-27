package com.taobao.math;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.*;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ObservableTest {

    @Test
    public void just() {
        Observable.just(1).subscribe(integer -> System.out.println(integer));
    }

    @Test
    public void defer() {
        //用来做缓存貌似可以
        Observable observable = Observable.defer(new Supplier<ObservableSource<?>>() {
            @Override
            public ObservableSource<?> get() throws Throwable {
                return Observable.just("1", "2");
            }
        });

        observable.subscribe(obj -> {
            System.out.println(obj);
        });

        observable.subscribe(obj -> {
            System.out.println(obj);
        });
    }

    @Test
    public void create() throws InterruptedException {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                emitter.setCancellable(() -> System.out.println("emitter canceled"));
                for (int i = 0; i < 10; i++) {
                    TimeUnit.MILLISECONDS.sleep(2000);
                    System.out.println(Thread.currentThread().getName());
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).blockingSubscribe(new Observer<Integer>() {
            Disposable disposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe");
                this.disposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println(Thread.currentThread().getName());
                System.out.println(integer);

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });

        // TimeUnit.MILLISECONDS.sleep(2000);
    }

    @Test
    public void from() {
        List<Integer> taskList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            taskList.add(i);
        }
        Observable.fromIterable(taskList).filter(integer -> integer > 2).blockingSubscribe(integer -> System.out.println(integer));

        ExecutorService service = Executors.newFixedThreadPool(10);
        Future<Integer> future = service.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(2000);
            return 1;
        });

        Observable.fromFuture(future).blockingSubscribe(integer -> System.out.println(integer));


    }

    @Test
    public void test() {
        long start = System.currentTimeMillis();
        List<Integer> taskList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            taskList.add(i);
        }
        List<Observable<Integer>> observableList = taskList.stream().map(task -> rxJobExecute(task)).collect(Collectors.toList());
        System.out.println("start");
        List<Integer> total = new ArrayList<>();
        Observable.merge(observableList).blockingSubscribe(integer -> total.add(integer));

        System.out.println(String.format(" currentThread:%s cost:%s", Thread.currentThread().getName(), (System.currentTimeMillis() - start)));
        System.out.println(String.format("result:%s", total));
    }

    public Observable<Integer> rxJobExecute(Integer task) {
        return Observable.fromCallable(() -> {
            Random random = new Random();
            Integer sleepTime = random.nextInt(4000);
            try{
                TimeUnit.MILLISECONDS.sleep(sleepTime);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            return task;
        }).subscribeOn(Schedulers.io()).timeout(2000, TimeUnit.MILLISECONDS, observer -> {
            System.out.println("timeout");
            observer.onNext(1);
            observer.onComplete();
        });
    }

    @Test
    public void test1() {
        List<Integer> taskList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            taskList.add(i);
        }
        Observable.<Integer>create(emitter -> {
            for (int task : taskList) {
                emitter.onNext(task);
            }
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).map(integer -> {
            TimeUnit.MILLISECONDS.sleep(1000);
            System.out.println(Thread.currentThread().getName());
            return integer * 2;
        }).blockingSubscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribeThread" + Thread.currentThread().getName());
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("onNextThread" + Thread.currentThread().getName());
                System.out.println(integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }
}
