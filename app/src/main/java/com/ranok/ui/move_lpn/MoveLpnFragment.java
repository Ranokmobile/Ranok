package com.ranok.ui.move_lpn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.MoveLpnFragmentBinding;
import com.ranok.mlkit.LivePreviewActivity;
import com.ranok.network.request.PackToLpnRequest;
import com.ranok.network.request.SplitLpnRequest;
import com.ranok.ui.base.BaseFragment;
import com.ranok.ui.dialogs.InputPlaceDialog;
import com.ranok.ui.main.MainActivity;

import ranok.mvvm.binding.ViewModelBindingConfig;

import static com.ranok.ui.dialogs.InputPlaceDialog.PLACE;


public class MoveLpnFragment extends BaseFragment<MoveLpnIView, MoveLpnVM, MoveLpnFragmentBinding>
        implements MoveLpnIView, TextView.OnEditorActionListener  {

    public static final int INPUT_PLACE_DIALOG_CODE = 2;
    public static final String LPN = "LPN", SPLIT = "SPLIT", PACK = "PACK";

    public static MoveLpnFragment getInstance(String sourceLpn, SplitLpnRequest splitLpnRequest){
        MoveLpnFragment fragment = new MoveLpnFragment();
        Bundle bundle = new Bundle();
        bundle.putString(LPN, sourceLpn);
        bundle.putParcelable(SPLIT, splitLpnRequest);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MoveLpnFragment getInstance(String sourceLpn, PackToLpnRequest packToLpnRequest){
        MoveLpnFragment fragment = new MoveLpnFragment();
        Bundle bundle = new Bundle();
        bundle.putString(LPN, sourceLpn);
        bundle.putParcelable(PACK, packToLpnRequest);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MoveLpnFragment getInstance(String sourceLpn){
        MoveLpnFragment fragment = new MoveLpnFragment();
        Bundle bundle = new Bundle();
        bundle.putString(LPN, sourceLpn);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected String getScreenTitle() {
        return "Перемещение НЗ";
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
        EditText et = getBinding().searchItemAimLpn.etCode;
        et.setImeOptions(EditorInfo.IME_ACTION_DONE );
        et.setOnEditorActionListener(this);
        et = getBinding().searchItemMovableLpn.etCode;
        et.setImeOptions(EditorInfo.IME_ACTION_DONE );
        et.setOnEditorActionListener(this);
        et = getBinding().searchItemAimPlace.etCode;
        et.setImeOptions(EditorInfo.IME_ACTION_DONE );
        et.setOnEditorActionListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_SEARCH) {
            mActivity.hideKeyboard();
            return true;
        }
        return false;
    }

    @Override
    public void startScanBarcode(MoveLpnVM.SearchWidgets client) {
        startActivityForResult(new Intent(getActivity(), LivePreviewActivity.class), client.tag);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INPUT_PLACE_DIALOG_CODE && resultCode == Activity.RESULT_OK) {
            String place ;
            place = data.getStringExtra(PLACE);
//            if (data.hasExtra(PLACE)) {
//                place = data.getExtras().getString(PLACE);
//            }
            getViewModel().getModel().setNewLpnPlace(place);
            getViewModel().onClick(new View(this.mActivity));
        } else {
            MoveLpnVM.SearchWidgets resItem = MoveLpnVM.SearchWidgets.getByTag(requestCode);
            if (resItem != MoveLpnVM.SearchWidgets.UNKNOWN && resultCode == Activity.RESULT_OK) {
                String barcode = data.getStringExtra("barcode");
                getViewModel().gotBarcode(barcode, resItem);
            }
        }
    }

    @Override
    public void showInputPlace(String header) {
        InputPlaceDialog actionsDialog = InputPlaceDialog.getInstance( header);
        actionsDialog.setTargetFragment(this, INPUT_PLACE_DIALOG_CODE);
        FragmentManager fm = this.getFragmentManager();
        if (fm != null) actionsDialog.show(fm, "DLG");
    }

    @Override
    public void closeScreen() {
        MainActivity activity = ((MainActivity)getActivity());
        if (activity!=null && !activity.isFinishing()) {
            activity.getSupportFragmentManager().popBackStack();
            if(getViewModel().transaction) activity.getSupportFragmentManager().popBackStack();
        }
    }

    @Nullable
    @Override
    public Class<MoveLpnVM> getViewModelClass() {
        return MoveLpnVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.move_lpn_fragment, BR.viewModel, getContext());
    }
}
