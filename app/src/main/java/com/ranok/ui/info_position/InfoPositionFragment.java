package com.ranok.ui.info_position;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.InfoPositionFragmentBinding;
import com.ranok.mlkit.LivePreviewActivity;
import com.ranok.ui.base.BaseFragment;
import com.ranok.ui.dialogs.SelectDialogFragment;
import com.ranok.utils.Utils;

import java.util.ArrayList;

import ranok.mvvm.binding.ViewModelBindingConfig;



public class InfoPositionFragment extends BaseFragment<InfoPositionIView, InfoPositionVM, InfoPositionFragmentBinding>
        implements InfoPositionIView, TextView.OnEditorActionListener {

    private static final int REQUEST_CODE_SERVICE = 99;
    InfoPositionPagerAdapter pagerAdapter;

    public static InfoPositionFragment getInstance(String code){
        InfoPositionFragment fragment = new InfoPositionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        fragment.setArguments(bundle);
        return fragment;
    }

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
        EditText et = getBinding().searchItem.etCode;
        pagerAdapter = new InfoPositionPagerAdapter(getChildFragmentManager(),getViewModel().getPagesCount());
        getBinding().vp.setAdapter(pagerAdapter);
        getBinding().tabLayout.setupWithViewPager(getBinding().vp);
        et.post(() -> Utils.selectText(et));
        et.setOnEditorActionListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().startSearch();
    }


    @Override
    public void updatePager(boolean hasRaceipt) {
        if (!hasRaceipt && getBinding().vp.getCurrentItem()==2) getBinding().vp.setCurrentItem(0,true);
        pagerAdapter.setCntPages(hasRaceipt ? 3 : 2);
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
        if (requestCode == REQUEST_CODE_SERVICE) {
            if (resultCode == SelectDialogFragment.RESULT_CODE) {
                int id = data.getIntExtra("ID", 0);
                getViewModel().searchByCode(String.valueOf(id));
                //getViewModel().serviceAdded(id);
            }
        }
    }

    @Override
    public void showSelectPositionDialog(ArrayList<? extends Parcelable> sourceList) {
        SelectPositionFragment.Builder builder = new SelectPositionFragment.Builder();
        DialogFragment fragment = builder.setSourceList(sourceList)
                .setItemLayout(R.layout.item_select)
                .setHeaderText("Выберите элемент")
                .build(this, REQUEST_CODE_SERVICE);
        fragment.show(mActivity.getSupportFragmentManager(), "DIALOG");
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
