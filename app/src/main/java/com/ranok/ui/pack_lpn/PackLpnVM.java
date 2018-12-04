package com.ranok.ui.pack_lpn;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.R;
import com.ranok.network.models.PlaceInfoModel;
import com.ranok.network.request.PackToLpnRequest;
import com.ranok.network.request.SplitLpnRequest;
import com.ranok.network.response.LpnOperationResponse;
import com.ranok.rx_bus.RxLpnOperation;
import com.ranok.ui.base.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ranok.annotation.State;


public class PackLpnVM extends BaseViewModel<PackLpnIView> {

    @State
    PlaceInfoModel model;


    public String getLpn() {
        return model.getAddress();
    }

    public String getFullPosition(){
        if (model!=null) return model.getItemCode()+" - "+model.getLot() ;
        else return "";
    }

    public PlaceInfoModel getModel() {
        return model;
    }

    private String inputQty;

    public String getInputQty() {
        return inputQty;
    }

    public void setInputQty(String inputQty) {
        this.inputQty = inputQty;
    }

    public String getQty(){
        return String.valueOf(model.getAvailQuantity());
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        if (savedInstanceState != null) StateHelperPackLpnVM.onRestoreInstanceState(this, savedInstanceState);
        if (arguments != null) {
            model = arguments.getParcelable("position");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        StateHelperPackLpnVM.onSaveInstanceState(this, bundle);
    }

    public void onClick(View v){
        if (inputQty == null || inputQty.isEmpty()) return;
        int qty = Integer.parseInt(inputQty);
        if (qty <= 0 || qty > model.getAvailQuantity()) {
            getViewOptional().showSnakeBar("Некоректное количество");
            return;
        }

        if (v.getId() == R.id.btnSplit){
            showLoader();
            compositeDisposable.add( //
                    netApi.packLpn(new PackToLpnRequest(model.getLot(), Integer.parseInt(model.getItemCode())
                            , qty, model.getAddress()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::processResponseSplit, this::processError)
            );

        } else if (v.getId() == R.id.btnSplitAndMove){
/*            compositeDisposable.add( //
                    netApi.packLpn(new PackToLpnRequest(model.getLot(), Integer.parseInt(model.getItemCode())
                            , qty, model.getAddress()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::processResponseSplitAndMove, this::processError)
            );
*/
            getViewOptional().showMoveFragment(new PackToLpnRequest(model.getLot(), Integer.parseInt(model.getItemCode())
                    , qty, model.getAddress()));

        }
    }
/*
    private void processResponseSplitAndMove(LpnOperationResponse response) {
        hideLoader();
        if (response.data.resultCode == 0) {
            getViewOptional().showMoveFragment(response.data.newLpnCode);
        } else {
            getViewOptional().showSnakeBar(response.data.resultMessage);
        }
    }
*/
    private void processResponseSplit(LpnOperationResponse response) {
        hideLoader();
        if (response.data.resultCode == 0) {
            RxLpnOperation.getInstance().sendLpnData(response.data.newLpnCode);
            getViewOptional().closeScreen();
        } else {
            getViewOptional().showSnakeBar(response.data.resultMessage);
        }
    }

}
