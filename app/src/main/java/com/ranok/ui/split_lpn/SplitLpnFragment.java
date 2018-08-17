package com.ranok.ui.split_lpn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.SplitLpnFragmentBinding;
import com.ranok.ui.base.BaseFragment;
import com.ranok.ui.move_lpn.MoveLpnFragment;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class SplitLpnFragment extends BaseFragment<SplitLpnIView, SplitLpnVM, SplitLpnFragmentBinding> implements SplitLpnIView {

    public static SplitLpnFragment getInstance(String sourceLpn){
        SplitLpnFragment fragment = new SplitLpnFragment();
        Bundle bundle = new Bundle();
        bundle.putString("lpn", sourceLpn);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
    }

    @Nullable
    @Override
    public Class<SplitLpnVM> getViewModelClass() {
        return SplitLpnVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.split_lpn_fragment, BR.viewModel, getContext());
    }
}
