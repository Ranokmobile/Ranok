package com.ranok.ui.reciept;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.LotCreateFragmentBinding;
import com.ranok.ui.base.BaseFragment;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class LotCreateFragment extends BaseFragment<LotCreateIView, LotCreateVM, LotCreateFragmentBinding>
        implements LotCreateIView {

    public static int NEW_LOT = 1, CHANGE_LOT = 2;



    @Override
    protected String getScreenTitle() {
        return "Создание партии";
    }

    public static LotCreateFragment getInstance(int type, String lot, String position){
        LotCreateFragment fragment = new LotCreateFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("lot", lot);
        bundle.putString("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
        //Твердость штуки
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item,
                getViewModel().getHardness());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getBinding().spinnerPosHardness.setAdapter(adapter);
        getBinding().spinnerPosHardness.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getViewModel().onPosHardhessSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Твердость упаковки
        ArrayAdapter<String> adapterPackHardness = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item,
                getViewModel().getHardness());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getBinding().spinnerPosHardness.setAdapter(adapterPackHardness);
        getBinding().spinnerPosHardness.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getViewModel().onPackHardhessSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Тип упаковки
        ArrayAdapter<String> adapterPackStandart = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item,
                getViewModel().getStandart());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getBinding().spinnerPosHardness.setAdapter(adapterPackStandart);
        getBinding().spinnerPosHardness.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getViewModel().onPackStandartSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void setSpinnerPosHardnessSelection(int i) {
        getBinding().spinnerPosHardness.setSelection(i);
    }

    @Override
    public void setSpinnerPackHardnessSelection(int i) {
        getBinding().spinnerPackHardness.setSelection(i);
    }

    @Override
    public void setSpinnerPackStandartSelection(int i) {
        getBinding().spinnerPackStandart.setSelection(i);
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
