package com.ranok.ui.unpack_lpn;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.R;
import com.ranok.network.models.LpnPositionModel;
import com.ranok.network.request.SplitLpnRequest;
import com.ranok.network.response.LpnOperationResponse;
import com.ranok.rx_bus.RxLpnOperation;
import com.ranok.ui.base.BaseViewModel;
import com.ranok.ui.split_lpn.StateHelperSplitLpnVM;
import com.ranok.utils.StringUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ranok.annotation.State;


public class UnpackLpnVM extends BaseViewModel<UnpackLpnIView> {

    @State
    LpnPositionModel model;

    @State
    String lpn="";

    public String getLpn() {
        return lpn;
    }

    public String getFullLpn() {
        return StringUtils.formatToLpn(lpn);
    }

    public String getFullPosition(){
        if (model!=null) return model.getCode()+" - "+model.getLot() ;
        else return "";
    }

    public LpnPositionModel getModel() {
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
        return String.valueOf(model.getAvailQty());
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        if (savedInstanceState != null) StateHelperUnpackLpnVM.onRestoreInstanceState(this, savedInstanceState);
        if (arguments != null) {
            lpn = arguments.getString("lpn");
            model = arguments.getParcelable("position");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        StateHelperUnpackLpnVM.onSaveInstanceState(this, bundle);
    }

    public void onClick(View v){
        if (inputQty == null || inputQty.isEmpty()) return;
        int qty = Integer.parseInt(inputQty);
        if (qty <= 0 || qty > model.getAvailQty()) {
            getViewOptional().showSnakeBar("Некоректное количество");
            return;
        }

        showLoader();
        if (v.getId() == R.id.btnSplit){
            compositeDisposable.add( //
                    netApi.splitLpn(new SplitLpnRequest(StringUtils.formatToLpn(getLpn()), model.getLot(),
                            Integer.parseInt(model.getCode()), qty, model.getISOAddress()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::processResponseSplit, this::processError)
            );

        } else if (v.getId() == R.id.btnSplitAndMove){
            compositeDisposable.add( //
                    netApi.splitLpn(new SplitLpnRequest(StringUtils.formatToLpn(getLpn()), model.getLot(),
                            Integer.parseInt(model.getCode()), qty, model.getISOAddress()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::processResponseSplitAndMove, this::processError)
            );
        }
    }

    private void processResponseSplitAndMove(LpnOperationResponse response) {
        hideLoader();
        if (response.data.resultCode == 0) {
            //RxLpnOperation.getInstance().sendLpnData(response.data.newLpnCode);
            getViewOptional().showMoveFragment(response.data.newLpnCode);
        }
    }

    private void processResponseSplit(LpnOperationResponse response) {
        hideLoader();
        if (response.data.resultCode == 0) {
            RxLpnOperation.getInstance().sendLpnData(response.data.newLpnCode);
            getViewOptional().closeScreen();
        }
    }
}
