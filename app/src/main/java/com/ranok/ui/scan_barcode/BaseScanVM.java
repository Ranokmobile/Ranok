package com.ranok.ui.scan_barcode;

import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.BR;
import com.ranok.mlkit.BarcodeScanCallback;
import com.ranok.ui.base.BaseViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ranok.annotation.State;


public class BaseScanVM extends BaseViewModel<BaseScanIView> implements BarcodeScanCallback {

    @State
    String barcode;

    @State
    String cnt = "0";

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Bindable
    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
        notifyPropertyChanged(BR.cnt);
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        StateHelperBaseScanVM.onRestoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        StateHelperBaseScanVM.onSaveInstanceState(this, bundle);
    }

    public void onClick(View v){
     getViewOptional().exit();
    }

    @Override
    public void gotBarcode(String barcode) {
        getViewOptional().pauseScan();
        showLoader();
        compositeDisposable.add(
                Single.just(1).delay(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processAddedBarcode, this::processFail)
        );
    }

    private void processFail(Throwable throwable) {
        hideLoader();
        getViewOptional().resumeScan();
    }

    private void processAddedBarcode(Integer integer) {
        hideLoader();
        setCnt(String.valueOf(Integer.parseInt(cnt)+1));
        getViewOptional().resumeScan();
    }
}
