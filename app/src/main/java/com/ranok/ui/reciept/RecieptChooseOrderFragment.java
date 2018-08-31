package com.ranok.ui.reciept;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.RecieptChooseOrderFragmentBinding;
import com.ranok.network.models.RecieptListModel;
import com.ranok.ui.base.BaseFragment;
import com.ranok.utils.Utils;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class RecieptChooseOrderFragment extends BaseFragment<RecieptChooseOrderIView,
        RecieptChooseOrderVM, RecieptChooseOrderFragmentBinding>
        implements RecieptChooseOrderIView, TextView.OnEditorActionListener {


    @Override
    protected String getScreenTitle() {
        return "Приход поступления";
    }

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

        getBinding().search.setActivated(true);
        getBinding().search.setQueryHint("фильтр");
        getBinding().search.onActionViewExpanded();
        getBinding().search.setIconified(false);
        getBinding().search.clearFocus();

        EditText et = getBinding().searchItem.etCode;
        et.post(() -> Utils.selectText(et));
        et.setOnEditorActionListener(this);
    }


    @Override
    public void showProcessing(RecieptListModel position) {
        mActivity.addFragment(RecieptProcessingFragment.getInstance(position));
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            mActivity.hideKeyboard();
            getViewModel().startSearch();
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Class<RecieptChooseOrderVM> getViewModelClass() {
        return RecieptChooseOrderVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.reciept_choose_order_fragment, BR.viewModel, getContext());
    }
}
