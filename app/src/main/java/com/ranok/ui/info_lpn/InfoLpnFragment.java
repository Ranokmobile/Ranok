package com.ranok.ui.info_lpn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.InfoLpnFragmentBinding;
import com.ranok.mlkit.LivePreviewActivity;
import com.ranok.ui.base.BaseFragment;
import com.ranok.utils.Utils;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class InfoLpnFragment extends BaseFragment<InfoLpnIView, InfoLpnVM, InfoLpnFragmentBinding>
        implements InfoLpnIView, TextView.OnEditorActionListener{

    @Override
    protected String getScreenTitle() {
        return "Информация о НЗ";
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
        EditText et = getBinding().searchItem.etCode;
        et.post(() -> Utils.selectText(et));

    }



    @Override
    public void startScanBarcode() {
        startActivityForResult(new Intent(getActivity(), LivePreviewActivity.class), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String barcode = data.getStringExtra("barcode");
            getViewModel().gotBarcode(barcode);
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            mActivity.hideKeyboard();
            getViewModel().startSearch();
            //getBinding().searchItem.ibSearch.performClick();
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Class<InfoLpnVM> getViewModelClass() {
        return InfoLpnVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.info_lpn_fragment, BR.viewModel, getContext());
    }
}
