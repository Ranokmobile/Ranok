package com.ranok.ui.main_menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.MainFragmentBinding;
import com.ranok.mlkit.LivePreviewActivity;
import com.ranok.ui.base.BaseFragment;
import com.ranok.ui.info_lpn.InfoLpnFragment;
import com.ranok.ui.info_position.InfoPositionFragment;
import com.ranok.ui.main.MainActivity;
import com.ranok.ui.scan_packages.ScanFragment;
import com.ranok.ui.scan_rfid.ScanRFIDFragment;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class MainFragment extends BaseFragment<MainIView, MainVM, MainFragmentBinding> implements MainIView {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.mainmenu, menu);
    }

    @Override
    public void showRFIDScan() {
        MainActivity activity = ((MainActivity)mActivity);
        if (activity!=null) {
            if (activity.isNfcAvailabe()) {
                activity.addFragment(new ScanRFIDFragment());
            } else {
                showSnakeBar(getString(R.string.nfc_not_available));
            }
        }
    }

    @Override
    public void showInfoPosition() {
        MainActivity activity = ((MainActivity)mActivity);
        if (activity!=null) {
            activity.addFragment(new InfoPositionFragment());
        }
    }

    @Override
    public void showInfoLpn() {
        MainActivity activity = ((MainActivity)mActivity);
        if (activity!=null) {
            activity.addFragment(new InfoLpnFragment());
        }
    }

    @Override
    public void startLoginActivity() {
        MainActivity activity = (MainActivity)mActivity;
        if (activity != null) {
            activity.startLoginActivity();
            activity.finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.barcode:
                startActivityForResult(new Intent(getActivity(), LivePreviewActivity.class), 1);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            mActivity.addFragment(new ScanFragment());
        }
    }

    @Nullable
    @Override
    public Class<MainVM> getViewModelClass() {
        return MainVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.main_fragment, BR.viewModel, getContext());
    }


}
