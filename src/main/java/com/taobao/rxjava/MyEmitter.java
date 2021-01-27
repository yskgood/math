package com.taobao.rxjava;

import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.atomic.AtomicReference;

public class MyEmitter extends AtomicReference<Disposable> {
}
