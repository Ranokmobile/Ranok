package com.ranok.ui.scan_barcode;

import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.BR;
import com.ranok.mlkit.BarcodeScanCallback;
import com.ranok.network.request.BarcodeRequest;
import com.ranok.network.response.BaseResponse;
import com.ranok.ui.base.BaseViewModel;

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
                netApi.packagebarcode(new BarcodeRequest(barcode))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::processAddedBarcode,this::processError)
        );
    }

    private void processAddedBarcode(BaseResponse baseResponse) {
        hideLoader();
        setCnt(String.valueOf(Integer.parseInt(cnt)+1));
        getViewOptional().resumeScan();
    }

    @Override
    protected void processError(Throwable throwable) {
        super.processError(throwable);
        getViewOptional().resumeScan();
    }
}
