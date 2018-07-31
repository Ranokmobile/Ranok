package com.ranok.ui.main.scan_packages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.RanokApp;
import com.ranok.databinding.ScanFragmentBinding;
import com.ranok.ui.base.BaseFragment;
import com.ranok.ui.scan_barcode.BaseScanFragment;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class ScanFragment extends BaseFragment<ScanIView, ScanVM, ScanFragmentBinding> implements ScanIView {


    @Override
    protected String getScreenTitle() {
        return "Сканирование";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
        RecyclerView rv = getBinding().rv;

        RecyclerView.Adapter adapter = getViewModel().getAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        rv.setLayoutManager(manager);
        if (adapter != null) rv.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(RanokApp.getApp(), data.getStringExtra("barcode"), Toast.LENGTH_SHORT).show();
            getViewModel().addBarcode(data.getStringExtra("barcode"));
        }
    }

    @Nullable
    @Override
    public Class<ScanVM> getViewModelClass() {
        return ScanVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.scan_fragment, BR.viewModel, getContext());
    }

    @Override
    public void exit() {
        FragmentActivity mActivity = getActivity();
        if (mActivity != null) mActivity.onBackPressed();
    }

    @Override
    public void scan() {
        mActivity.addFragment(new BaseScanFragment());
        //startActivityForResult(new Intent(getActivity(), LivePreviewActivity.class), 1);
    }


}
