package com.ranok.ui.main.scan_packages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.adapters.RecyclerBindingAdapter;
import com.ranok.network.response.PackageBarcodeResponseData;
import com.ranok.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;


public class ScanVM extends BaseViewModel<ScanIView>  {

    List<PackageBarcodeResponseData> listBarcodes = new ArrayList<>();
    RecyclerBindingAdapter<PackageBarcodeResponseData> adapter
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
        getViewOptional().exit();
    }
}

