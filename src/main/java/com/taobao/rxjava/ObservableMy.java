package com.taobao.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

public class ObservableMy<T> extends Observable<T> {
    @Override
    protected void subscribeActual(@NonNull Observer<? super T> observer) {

    }
}
