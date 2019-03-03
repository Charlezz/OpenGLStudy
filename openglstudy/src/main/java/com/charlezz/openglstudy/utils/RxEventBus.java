package com.charlezz.openglstudy.utils;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxEventBus<T>  {
    public RxEventBus() { }

    private PublishSubject<T> bus = PublishSubject.create();

    public void send(T o) {
        bus.onNext(o);
    }

    public Observable<T> toObserverable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}