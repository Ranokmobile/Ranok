package com.ranok.ui.scan_barcode;

import android.databinding.Bindable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.RanokApp;
import com.ranok.mlkit.BarcodeScanCallback;
import com.ranok.network.request.BarcodeRequest;
import com.ranok.network.response.PackageBarcodeResponse;
import com.ranok.network.response.PackageBarcodeResponseData;
import com.ranok.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ranok.annotation.State;


public class BaseScanVM extends BaseViewModel<BaseScanIView> implements BarcodeScanCallback {

    private MediaPlayer mediaPlayer;
    private List<PackageBarcodeResponseData> responsesList = new ArrayList<>();

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

    private void processAddedBarcode(PackageBarcodeResponse response) {
        //responsesList.add(response.data);
        getViewOptional().barcodeAccepted(response.data);
        hideLoader();
        if (response.data.code == 0){
            playSound(R.raw.scanner);
            setCnt(String.valueOf(Integer.parseInt(cnt)+1));
        } else if (response.data.code == -1){
            playSound(R.raw.error);
            getViewOptional().showSnakeBar(R.string.package_not_found);
        } else if (response.data.code == 1){
            playSound(R.raw.error);
            getViewOptional().showSnakeBar(R.string.package_already_exists);
        } else if (response.data.code == -2) { //ves.conv_sessions exp
            playSound(R.raw.error);
            getViewOptional().showSnakeBar(R.string.session_expired);
        } else if (response.data.code == -3) { //aps.setting_code = 'parsel_scan_outcome';
            playSound(R.raw.error);
            getViewOptional().showSnakeBar(R.string.server_error);
        }
        getViewOptional().resumeScan();
    }

    @Override
    protected void processError(Throwable throwable) {
        super.processError(throwable);
        getViewOptional().resumeScan();
    }

    private void playSound(int id){
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = MediaPlayer.create(RanokApp.getApp().getApplicationContext(), id);
        if (mediaPlayer!=null){
            mediaPlayer.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
