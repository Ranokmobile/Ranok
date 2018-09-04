package com.ranok.rx_bus;

import com.ranok.network.response.CreateLotResponse;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class RxLotCreated {
    private static RxLotCreated instance;

    private Subject<CreateLotResponse> subject = BehaviorSubject.create();


    public static RxLotCreated getInstance() {
        if (instance == null) {
            instance = new RxLotCreated();
        }
        return instance;
    }

    public void sendLpnData(CreateLotResponse data) {
        subject.onNext(data);
    }


    public Observable<CreateLotResponse> getEvents() {
        return subject;
    }
}
