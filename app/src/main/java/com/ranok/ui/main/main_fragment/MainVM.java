package com.ranok.ui.main.main_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.orhanobut.hawk.Hawk;
import com.ranok.R;
import com.ranok.ui.base.BaseViewModel;
import com.ranok.utils.Consts;

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
        switch (v.getId()) {
            case R.id.scan_package :
                getViewOptional().showRFIDScan();
                break;
            case R.id.logout :
                Hawk.delete(Consts.TOKEN);
                getViewOptional().startLoginActivity();
                break;
        }


    }
}

