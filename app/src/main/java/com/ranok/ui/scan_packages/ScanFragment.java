package com.ranok.ui.scan_packages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.ScanFragmentBinding;
import com.ranok.ui.base.BaseFragment;
import com.ranok.ui.main.MainActivity;
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
        getViewModel().setListBarcodes(((MainActivity)mActivity).getScanPackagesResults());
        RecyclerView rv = getBinding().rv;
        RecyclerView.Adapter adapter = getViewModel().getAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        rv.setLayoutManager(manager);
        if (adapter != null) rv.setAdapter(adapter);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
//            Toast.makeText(RanokApp.getApp(), data.getStringExtra("barcode"), Toast.LENGTH_SHORT).show();
//            getViewModel().addBarcode(data.getStringExtra("barcode"));
//        }
//    }

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

    @Override
    public void createDialog() {
        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
        ad.setCancelable(false);
        ad.setTitle("");
        ad.setMessage("Вы уверенны что хотите завершить сканирование?");
        ad.setPositiveButton("Да",  (dialog, arg1) -> getViewModel().commitScan());
        ad.setNegativeButton("Нет", (dialogInterface, i) -> dialogInterface.cancel());
        ad.show();
    }

}
