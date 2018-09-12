package com.ranok.ui.reciept;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.CheckRecieptFragmentBinding;
import com.ranok.mlkit.LivePreviewActivity;
import com.ranok.ui.base.BaseFragment;
import com.ranok.utils.Utils;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class CheckRecieptFragment extends BaseFragment<CheckRecieptIView, CheckRecieptIVM, CheckRecieptFragmentBinding>
        implements CheckRecieptIView,  TextView.OnEditorActionListener {




    @Override
    protected String getScreenTitle() {
        return "Контроль поступления";
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, getViewModel().getQualities());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getBinding().spinnerQuality.setAdapter(adapter);
        getBinding().spinnerQuality.setSelection(getViewModel().getQualityCode().npp);
        getBinding().spinnerQuality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getViewModel().spinnerItemSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        EditText et = getBinding().searchLpn.etCode;
        et.post(() -> Utils.selectText(et));
        et.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_SEARCH) {
            mActivity.hideKeyboard();
            getViewModel().startSearch();
            return true;
        }
        return false;
    }

    @Override
    public void startScanBarcode() {
        startActivityForResult(new Intent(getActivity(), LivePreviewActivity.class),1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String barcode = data.getStringExtra("barcode");
            getViewModel().gotBarcode(barcode);
        }
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
