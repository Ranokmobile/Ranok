package com.ranok.ui.info_position;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.InfoPositionFragmentBinding;
import com.ranok.ui.base.BaseFragment;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class InfoPositionFragment extends BaseFragment<InfoPositionIView, InfoPositionVM, InfoPositionFragmentBinding>
        implements InfoPositionIView, TextView.OnEditorActionListener {

    @Override
    protected String getScreenTitle() {
        return getString(R.string.about_position);
    }

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
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            mActivity.hideKeyboard();
            getBinding().searchItem.ibSearch.performClick();
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Class<InfoPositionVM> getViewModelClass() {
        return InfoPositionVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.info_position_fragment, BR.viewModel, getContext());
    }
}
