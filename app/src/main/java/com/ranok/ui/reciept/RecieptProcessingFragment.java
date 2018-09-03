package com.ranok.ui.reciept;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.RecieptProcessingFragmentBinding;
import com.ranok.network.models.RecieptListModel;
import com.ranok.ui.base.BaseFragment;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class RecieptProcessingFragment extends BaseFragment<RecieptProcessingIView,
        RecieptProcessingVM, RecieptProcessingFragmentBinding> implements RecieptProcessingIView {

    @Override
    protected String getScreenTitle() {
        return "Приход поступления";
    }

    public static RecieptProcessingFragment getInstance(RecieptListModel position){
        RecieptProcessingFragment fragment = new RecieptProcessingFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, getViewModel().getData());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getBinding().spinnerLot.setAdapter(adapter);
        getBinding().spinnerLot.setSelection(getViewModel().selectedLot);
        getBinding().spinnerLot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getViewModel().spinnerItemSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void showLot(int type, String lot, String position) {
        mActivity.addFragment(LotCreateFragment.getInstance(type, lot, position));
    }

    @Nullable
    @Override
    public Class<RecieptProcessingVM> getViewModelClass() {
        return RecieptProcessingVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.reciept_processing_fragment, BR.viewModel, getContext());
    }
}
