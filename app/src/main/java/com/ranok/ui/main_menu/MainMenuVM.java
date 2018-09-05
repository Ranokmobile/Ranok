package com.ranok.ui.main_menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.orhanobut.hawk.Hawk;
import com.ranok.R;
import com.ranok.network.response.BaseResponse;
import com.ranok.ui.base.BaseViewModel;
import com.ranok.utils.Consts;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ranok.annotation.State;


public class MainMenuVM extends BaseViewModel<MainMenuIView>  {

    @State
    String test;


    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        StateHelperMainMenuVM.onRestoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        StateHelperMainMenuVM.onSaveInstanceState(this, bundle);
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.scan_package :
                getViewOptional().showRFIDScan();
                break;
            case R.id.logout :
                logOut();
                break;
            case R.id.position_info :
                getViewOptional().showInfoPosition();
                break;
            case R.id.lpn_info :
                getViewOptional().showInfoLpn();
                break;
            case R.id.place_info:
                getViewOptional().showInfoPlace();
                break;
            case R.id.reciept:
                getViewOptional().reciept();
                break;
            case R.id.check_reciept:
                getViewOptional().checkReciept();
                break;
        }


    }

    private void logOut() {
        compositeDisposable.add(
        netApi.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processLogout,this::processError)
        );
    }

    private void processLogout(BaseResponse object) {
        Hawk.delete(Consts.TOKEN);
        getViewOptional().startLoginActivity();
    }
}

