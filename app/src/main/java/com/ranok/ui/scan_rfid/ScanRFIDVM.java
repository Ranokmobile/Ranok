package com.ranok.ui.scan_rfid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ranok.rx_bus.RxRFIDEvent;
import com.ranok.ui.base.BaseViewModel;


public class ScanRFIDVM extends BaseViewModel<ScanRFIDIView> {

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
//        RxRFIDEvent.getInstance().getEvents().
//                flatMap(new Function<String, String>() {
//                    @Override
//                    public String apply(String s) throws Exception {
//                        return netApi.login();
//                    }
//                });

        compositeDisposable.add(RxRFIDEvent.getInstance().getEvents()
                .subscribe(this::gotRFID));
    }

    private void gotRFID(String s) {
        Log.d("RFID", s);
        getViewOptional().startScanPackages();

    }
}
