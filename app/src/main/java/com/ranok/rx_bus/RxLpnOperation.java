package com.ranok.rx_bus;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class RxLpnOperation {
    private static RxLpnOperation instance;

    private Subject<String> subject = BehaviorSubject.create();


    public static RxLpnOperation getInstance() {
        if (instance == null) {
            instance = new RxLpnOperation();
        }
        return instance;
    }

    public void sendLpnData(String data) {
        subject.onNext(data);
    }


    public Observable<String> getEvents() {
        return subject;
    }
}
