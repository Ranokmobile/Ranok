package com.ranok.ui.scan_rfid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ranok.network.request.RfidRequest;
import com.ranok.network.response.BaseResponse;
import com.ranok.rx_bus.RxRFIDEvent;
import com.ranok.ui.base.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


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
                .filter(s -> !s.isEmpty())
                .subscribe(this::gotRFID));
    }

    private void gotRFID(String s) {
        Log.d("ranok ScanRFIDVM", "gotRfid");
        RxRFIDEvent.getInstance().sendRFIDData("");
        showLoader();
        compositeDisposable.add(
                netApi.rfid(new RfidRequest(s, ""))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::processOk,this::processError)
        );


    }

    private void processOk(BaseResponse baseResponse) {
        hideLoader();
        getViewOptional().startScanPackages();

    }
}
