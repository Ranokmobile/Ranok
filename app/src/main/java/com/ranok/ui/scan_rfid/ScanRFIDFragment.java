package com.ranok.ui.scan_rfid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.ScanRfidFragmentBinding;
import com.ranok.ui.base.BaseFragment;
import com.ranok.ui.main.MainActivity;
import com.ranok.ui.scan_packages.ScanFragment;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class ScanRFIDFragment extends BaseFragment<ScanRFIDIView, ScanRFIDVM, ScanRfidFragmentBinding> implements ScanRFIDIView {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
    }

    @Override
    public void startScanPackages() {
        MainActivity activity = ((MainActivity)getActivity());
        if (activity!=null) {
            activity.getScanPackagesResults().clear();
            activity.getSupportFragmentManager().popBackStack();
            activity.addFragment(new ScanFragment());
        }
    }

    @Nullable
    @Override
    public Class<ScanRFIDVM> getViewModelClass() {
        return ScanRFIDVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.scan_rfid_fragment, BR.viewModel, getContext());
    }
}
