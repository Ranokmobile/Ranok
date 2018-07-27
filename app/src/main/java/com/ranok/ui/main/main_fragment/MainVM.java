package com.ranok.ui.main.main_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ranok.annotation.State;
import com.ranok.ui.base.BaseViewModel;


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
}

