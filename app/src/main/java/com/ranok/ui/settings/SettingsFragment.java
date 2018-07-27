package com.ranok.ui.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import ranok.mvvm.binding.ViewModelBindingConfig;
import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.SettingsFragmentBinding;
import com.ranok.ui.base.BaseFragment;


public class SettingsFragment extends BaseFragment<SettingsIView, SettingsVM, SettingsFragmentBinding> implements SettingsIView {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
    }

    @Nullable
    @Override
    public Class<SettingsVM> getViewModelClass() {
        return SettingsVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.settings_fragment, BR.viewModel, getContext());
    }
}
