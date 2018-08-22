package com.ranok.ui.pack_lpn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.PackLpnFragmentBinding;
import com.ranok.network.models.PlaceInfoModel;
import com.ranok.ui.base.BaseFragment;
import com.ranok.ui.move_lpn.MoveLpnFragment;
import com.ranok.utils.Utils;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class PackLpnFragment extends BaseFragment<PackLpnIView, PackLpnVM, PackLpnFragmentBinding>
        implements PackLpnIView, TextView.OnEditorActionListener {

    @Override
    protected String getScreenTitle() {
        return "Упаковать в НЗ";
    }

    public static PackLpnFragment getInstance(PlaceInfoModel position){
        PackLpnFragment fragment = new PackLpnFragment();
        Bundle bundle = new Bundle();
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
    public Class<PackLpnVM> getViewModelClass() {
        return PackLpnVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.pack_lpn_fragment, BR.viewModel, getContext());
    }
}
