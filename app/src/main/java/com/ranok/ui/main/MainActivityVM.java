package com.ranok.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orhanobut.hawk.Hawk;
import com.ranok.RanokApp;
import com.ranok.network.request.NewFcmTokenRequest;
import com.ranok.network.response.BaseResponse;
import com.ranok.network.response.PackageBarcodeResponseData;
import com.ranok.ui.base.BaseViewModel;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ranok.annotation.State;


/**
 * Created by Sasha on 07.04.2018.
 */

public class MainActivityVM extends BaseViewModel<MainActivityIView> {

    @State
    public ArrayList<PackageBarcodeResponseData> packageScanResults = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        StateHelperMainActivityVM.onRestoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        StateHelperMainActivityVM.onSaveInstanceState(this, bundle);
    }


    public void checkToken() {
        if (Hawk.get("PUSHTOKENSENT", false)) return;

        String refreshedToken = Hawk.get("PUSHTOKEN", "");
        if (refreshedToken.isEmpty()) {
            return;
        }
        compositeDisposable.add(
        RanokApp.getApp().getNetApi().newPushToken(new NewFcmTokenRequest(refreshedToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processResponse, this::processSilence)
        );
    }

    private void processSilence(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void processResponse(BaseResponse baseResponse) {

    }
}
