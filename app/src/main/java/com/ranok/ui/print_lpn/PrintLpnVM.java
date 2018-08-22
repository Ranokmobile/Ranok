package com.ranok.ui.print_lpn;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.R;
import com.ranok.network.models.PlaceInfoModel;
import com.ranok.network.request.PrintLpnRequest;
import com.ranok.network.response.LpnOperationResponse;
import com.ranok.ui.base.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ranok.annotation.State;


public class PrintLpnVM extends BaseViewModel<PrintLpnIView> {
    @State
    PlaceInfoModel model;

    @State
    String lpn="";

    public String getLpn() {
        return lpn;
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
        if (savedInstanceState != null) StateHelperPrintLpnVM.onRestoreInstanceState(this, savedInstanceState);
        if (arguments != null) {
            lpn = arguments.getString("lpn");
            model = arguments.getParcelable("position");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        StateHelperPrintLpnVM.onSaveInstanceState(this, bundle);
    }

    public void onClick(View v){
        showLoader();
        if (v.getId() == R.id.btnGo){
            compositeDisposable.add( //
                    netApi.printLpn(new PrintLpnRequest(model.getLpn()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::processResponse, this::processError)
            );

        }
    }


    private void processResponse(LpnOperationResponse response) {
        hideLoader();
        if (response.data.resultCode == 0) {
            showToast("Запрос успешно выполнен");
            getViewOptional().closeScreen();
        } else {
            getViewOptional().showSnakeBar(response.data.resultMessage);
        }
    }


}
