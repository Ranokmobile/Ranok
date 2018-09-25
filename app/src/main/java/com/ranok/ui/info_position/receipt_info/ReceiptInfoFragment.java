package com.ranok.ui.info_position.receipt_info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.ReceiptInfoFragmentBinding;
import com.ranok.ui.base.BaseFragment;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class ReceiptInfoFragment extends BaseFragment<ReceiptInfoIView, ReceiptInfoVM, ReceiptInfoFragmentBinding> implements ReceiptInfoIView {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
        RecyclerView rv = getBinding().rv;
        RecyclerView.Adapter adapter = getViewModel().getAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        rv.setLayoutManager(manager);
        DividerItemDecoration decor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        decor.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.divider_horizontal_gray));
        rv.addItemDecoration(decor);
        if (adapter != null) rv.setAdapter(adapter);
    }

    @Nullable
    @Override
    public Class<ReceiptInfoVM> getViewModelClass() {
        return ReceiptInfoVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.receipt_info_fragment, BR.viewModel, getContext());
    }
}
