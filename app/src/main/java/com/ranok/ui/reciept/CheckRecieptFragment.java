package com.ranok.ui.reciept;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.CheckRecieptFragmentBinding;
import com.ranok.mlkit.LivePreviewActivity;
import com.ranok.ui.base.BaseFragment;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class CheckRecieptFragment extends BaseFragment<CheckRecieptIView, CheckRecieptIVM, CheckRecieptFragmentBinding> implements CheckRecieptIView {


    @Override
    protected String getScreenTitle() {
        return "Контроль поступления";
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
    }

    @Override
    public void startScanBarcode() {
        startActivityForResult(new Intent(getActivity(), LivePreviewActivity.class),1);
    }

    @Nullable
    @Override
    public Class<CheckRecieptIVM> getViewModelClass() {
        return CheckRecieptIVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.check_reciept_fragment, BR.viewModel, getContext());
    }
}
