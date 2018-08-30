package com.ranok.ui.reciept;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.RecieptChooseOrderFragmentBinding;
import com.ranok.ui.base.BaseFragment;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class RecieptChooseOrderFragment extends BaseFragment<RecieptChooseOrderIView, RecieptChooseOrderVM, RecieptChooseOrderFragmentBinding> implements RecieptChooseOrderIView {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
    }

    @Nullable
    @Override
    public Class<RecieptChooseOrderVM> getViewModelClass() {
        return RecieptChooseOrderVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.reciept_choose_order_fragment, BR.viewModel, getContext());
    }
}
