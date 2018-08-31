package com.ranok.ui.reciept;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.LotCreateFragmentBinding;
import com.ranok.ui.base.BaseFragment;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class LotCreateFragment extends BaseFragment<LotCreateIView, LotCreateVM, LotCreateFragmentBinding> implements LotCreateIView {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
    }

    @Nullable
    @Override
    public Class<LotCreateVM> getViewModelClass() {
        return LotCreateVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.lot_create_fragment, BR.viewModel, getContext());
    }
}
