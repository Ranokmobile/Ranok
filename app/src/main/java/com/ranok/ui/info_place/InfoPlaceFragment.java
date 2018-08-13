package com.ranok.ui.info_place;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.InfoPlaceFragmentBinding;
import com.ranok.ui.base.BaseFragment;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class InfoPlaceFragment extends BaseFragment<InfoPlaceIView, InfoPlaceVM, InfoPlaceFragmentBinding> implements InfoPlaceIView {

    @Override
    protected String getScreenTitle() {
        return "Информация о ячейке";
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
//        EditText et = getBinding().searchItem.etCode;
  //      et.post(() -> Utils.selectText(et));

    }

    @Nullable
    @Override
    public Class<InfoPlaceVM> getViewModelClass() {
        return InfoPlaceVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.info_place_fragment, BR.viewModel, getContext());
    }
}
