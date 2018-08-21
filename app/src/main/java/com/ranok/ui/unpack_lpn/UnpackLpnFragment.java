package com.ranok.ui.unpack_lpn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.SplitLpnFragmentBinding;
import com.ranok.network.models.LpnPositionModel;
import com.ranok.ui.base.BaseFragment;
import com.ranok.ui.move_lpn.MoveLpnFragment;
import com.ranok.utils.Utils;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class UnpackLpnFragment extends BaseFragment<UnpackLpnIView, UnpackLpnVM, SplitLpnFragmentBinding>
        implements UnpackLpnIView, TextView.OnEditorActionListener {

    public static UnpackLpnFragment getInstance(String sourceLpn, LpnPositionModel position){
        UnpackLpnFragment fragment = new UnpackLpnFragment();
        Bundle bundle = new Bundle();
        bundle.putString("lpn", sourceLpn);
        bundle.putParcelable("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
        EditText et = getBinding().editText;
        et.post(() -> Utils.selectText(et));
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            mActivity.hideKeyboard();
            return true;
        }
        return false;
    }

    @Override
    public void closeScreen() {
        mActivity.getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public void showMoveFragment(String newLpn) {
        mActivity.getSupportFragmentManager().popBackStackImmediate();
        mActivity.addFragment(MoveLpnFragment.getInstance(newLpn));
    }

    @Nullable
    @Override
    public Class<UnpackLpnVM> getViewModelClass() {
        return UnpackLpnVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.split_lpn_fragment, BR.viewModel, getContext());
    }
}
