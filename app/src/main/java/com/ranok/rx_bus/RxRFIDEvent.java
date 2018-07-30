package com.ranok.rx_bus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxRFIDEvent {
    private static RxRFIDEvent instance;

    private Subject<String> subject = PublishSubject.create();


    public static RxRFIDEvent getInstance() {
        if (instance == null) {
            instance = new RxRFIDEvent();
        }
        return instance;
    }

    public void sendRFIDData(String data) {
        subject.onNext(data);
    }


    public Observable<String> getEvents() {
        return subject;
    }
}

