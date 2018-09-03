package com.ranok.ui.reciept;

import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.network.models.RecieptListModel;
import com.ranok.ui.base.BaseViewModel;
import com.ranok.utils.StringUtils;

import ranok.annotation.State;

import static com.ranok.ui.reciept.LotCreateFragment.CHANGE_LOT;
import static com.ranok.ui.reciept.LotCreateFragment.NEW_LOT;


public class RecieptProcessingVM extends BaseViewModel<RecieptProcessingIView> {

    @State
    RecieptListModel position;

    @State
    String inputQty;

    private String[] data = {};

    @State
    int selectedLot;


    public String[] getData() {
        return data;
    }

    public String getInputQty() {
        return inputQty;
    }

    public void setInputQty(String inputQty) {
        this.inputQty = inputQty;
        notifyPropertyChanged(BR.recieptEnabled);
    }

    public RecieptListModel getPosition() {
        return position;
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        StateHelperRecieptProcessingVM.onRestoreInstanceState(this,savedInstanceState);
        if (arguments != null) {
            position = arguments.getParcelable("position");
        }

        if (position != null) {
            data = position.getLots().split(",");
        }
    }

    public void spinnerItemSelected(int i){
        selectedLot = i;
    }

    public String getSelectedLot() {
        if (position != null) {
          return data[selectedLot];
        }
        return "";
    }

    public void onShowLot(View v){
        int type = v.getId() == R.id.ibAddLot ? NEW_LOT : CHANGE_LOT;
        getViewOptional().showLot(type, getSelectedLot(), position.getItemCode());
    }

    @Bindable
    public boolean isRecieptEnabled(){
        return (!StringUtils.isEmpty(inputQty) && Integer.parseInt(inputQty) <= position.getAvailQuantity() );
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        StateHelperRecieptProcessingVM.onSaveInstanceState(this, bundle);
    }
}
