package com.ranok.ui.move_lpn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.MoveLpnFragmentBinding;
import com.ranok.mlkit.LivePreviewActivity;
import com.ranok.ui.base.BaseFragment;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class MoveLpnFragment extends BaseFragment<MoveLpnIView, MoveLpnVM, MoveLpnFragmentBinding> implements MoveLpnIView {

    @Override
    protected String getScreenTitle() {
        return "Перемещение НЗ";
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
    }

    @Override
    public void startScanBarcode(MoveLpnVM.SearchWidgets client) {
        startActivityForResult(new Intent(getActivity(), LivePreviewActivity.class), client.tag);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MoveLpnVM.SearchWidgets resItem = MoveLpnVM.SearchWidgets.getByTag(requestCode);
        if (resItem != MoveLpnVM.SearchWidgets.UNKNOWN && resultCode == Activity.RESULT_OK) {
            String barcode = data.getStringExtra("barcode");
            getViewModel().gotBarcode(barcode, resItem);
        }
    }

    @Nullable
    @Override
    public Class<MoveLpnVM> getViewModelClass() {
        return MoveLpnVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.move_lpn_fragment, BR.viewModel, getContext());
    }
}
