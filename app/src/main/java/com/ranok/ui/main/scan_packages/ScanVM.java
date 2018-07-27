package com.ranok.ui.main.scan_packages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.ranok.R;
import com.ranok.BR;
import com.ranok.adapters.RecyclerBindingAdapter;
import com.ranok.ui.base.BaseViewModel;


public class ScanVM extends BaseViewModel<ScanIView>  {



    List<String> listBarcodes = new ArrayList<>();
    RecyclerBindingAdapter<String> adapter = new RecyclerBindingAdapter<>(R.layout.item_barcode, BR.viewModel, listBarcodes);


    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
    }

    public RecyclerBindingAdapter<String> getAdapter() {
        return adapter;
    }

    public void addBarcode(String barcode){
        listBarcodes.add(barcode);
        adapter.setItems(listBarcodes);
    }

    public void scanClick(View v){
        getViewOptional().scan();
    }

    public void stopClick(View v){
        getViewOptional().exit();
    }
}

