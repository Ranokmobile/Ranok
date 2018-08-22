package com.ranok.rx_bus;

import com.ranok.network.response.PositionInfoByBarcodeData;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class RxPosInfo {
    private static RxPosInfo instance;

    private Subject<PositionInfoByBarcodeData> subject = BehaviorSubject.create();


    public static RxPosInfo getInstance() {
        if (instance == null) {
            instance = new RxPosInfo();
        }
        return instance;
    }

    public void sendData(PositionInfoByBarcodeData data) {
        subject.onNext(data);
    }


    public Observable<PositionInfoByBarcodeData> getEvents() {
        return subject;
    }
}



