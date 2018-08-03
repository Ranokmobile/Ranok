package com.ranok.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ranok.network.response.PackageBarcodeResponseData;
import com.ranok.ui.base.BaseViewModel;

import java.util.ArrayList;

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
}
