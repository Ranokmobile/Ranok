package com.ranok.ui.scan_packages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.RanokApp;
import com.ranok.adapters.RecyclerBindingAdapter;
import com.ranok.network.response.PackageBarcodeResponseData;
import com.ranok.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ScanVM extends BaseViewModel<ScanIView>  {

    private List<PackageBarcodeResponseData> listBarcodes = new ArrayList<>();
    private RecyclerBindingAdapter<PackageBarcodeResponseData> adapter
            = new RecyclerBindingAdapter<>(R.layout.item_barcode, BR.viewModel, listBarcodes);

    public void setListBarcodes(List<PackageBarcodeResponseData> listBarcodes) {
        this.listBarcodes = listBarcodes;
        adapter.setItems(listBarcodes);
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
    }



    public RecyclerBindingAdapter<PackageBarcodeResponseData> getAdapter() {
        return adapter;
    }

    public void scanClick(View v){
        getViewOptional().scan();
    }

    public void stopClick(View v){
        getViewOptional().createDialog();
    }

    public void commitScan(){
        showLoader();
        compositeDisposable.add(
                netApi.addMovement()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::processAddedMovement,this::processError)
        );
    }

    private void processAddedMovement(PackageBarcodeResponseData baseResponse) {
        hideLoader();
        if (baseResponse.code == 0) {
            showToast(RanokApp.getApp().getString(R.string.scan_packages_complete));
            getViewOptional().exit();
        } else {
            getViewOptional().showSnakeBar("Ошибка");
        }
    }
}

