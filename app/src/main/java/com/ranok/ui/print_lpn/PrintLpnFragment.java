package com.ranok.ui.print_lpn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.PrintLpnFragmentBinding;
import com.ranok.network.models.PlaceInfoModel;
import com.ranok.ui.base.BaseFragment;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class PrintLpnFragment extends BaseFragment<PrintLpnIView, PrintLpnVM, PrintLpnFragmentBinding> implements PrintLpnIView {

    @Override
    protected String getScreenTitle() {
        return "Печать содержимого НЗ";
    }


    public static PrintLpnFragment getInstance(PlaceInfoModel position){
        PrintLpnFragment fragment = new PrintLpnFragment();
        Bundle bundle = new Bundle();
        bundle.putString("lpn", position.getLpn());
        bundle.putParcelable("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
    }

    @Override
    public void closeScreen() {
        mActivity.getSupportFragmentManager().popBackStackImmediate();
    }


    @Nullable
    @Override
    public Class<PrintLpnVM> getViewModelClass() {
        return PrintLpnVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.print_lpn_fragment, BR.viewModel, getContext());
    }
}
