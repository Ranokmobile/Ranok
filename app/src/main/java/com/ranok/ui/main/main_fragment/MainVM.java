package com.ranok.ui.main.main_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.ranok.RanokApp;
import com.ranok.ui.base.BaseViewModel;

import ranok.annotation.State;


public class MainVM extends BaseViewModel<MainIView>  {

    @State
    String test;


    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        StateHelperMainVM.onRestoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        StateHelperMainVM.onSaveInstanceState(this, bundle);
    }

    public void onClick(View v){
        getViewOptional().showRFIDScan();

        /*compositeDisposable.add(RanokApp.getApp().getNetApi()
                .login(new LoginRequest("azhuk", "bug1979"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processResponse, this::processError));
*/
        //Toast.makeText(RanokApp.getApp(),"Click",Toast.LENGTH_SHORT).show();
    }

    private void processResponse(String s) {
        Toast.makeText(RanokApp.getApp(),s,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void processError(Throwable throwable) {
        super.processError(throwable);
    }
}

