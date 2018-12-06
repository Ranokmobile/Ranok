package com.ranok.ui.reciept;

import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.network.models.RecieptListModel;
import com.ranok.network.request.RecieptOrderRequest;
import com.ranok.network.response.CreateLotResponse;
import com.ranok.network.response.CreateLotResponseData;
import com.ranok.network.response.RecieptOrderResponse;
import com.ranok.rx_bus.RxLotCreated;
import com.ranok.ui.base.BaseViewModel;
import com.ranok.utils.StringUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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

        if (position != null && position.getLots() != null) {
            data = position.getLots().split(",");
        }

        compositeDisposable.add(
                RxLotCreated.getInstance().getEvents()
                        .filter(i->i.data.resultCode==0)
                        .subscribe(this::onNewLotRecieved));
    }

    private void onNewLotRecieved(CreateLotResponse createLotResponse) {
        String newLot = createLotResponse.data.newLotCode;
        RxLotCreated.getInstance().sendLpnData(new CreateLotResponse(new CreateLotResponseData(1,"","")));
        if (!StringUtils.isEmpty(newLot)) {
            if (position != null) {
                data = (position.getLots()+","+newLot).split(",");
                selectedLot = data.length-1;
                getViewOptional().changeSpinner();
            }
        }
    }

    public void spinnerItemSelected(int i){
        selectedLot = i;
    }

    public String getSelectedLot() {
        if (position != null && data.length > 0) {
          return data[selectedLot];
        }
        return "";
    }

    public void onShowLot(View v){
        int type = v.getId() == R.id.ibAddLot ? NEW_LOT : CHANGE_LOT;
        getViewOptional().showLot(type, getSelectedLot(), position);
    }

    @Bindable
    public boolean isRecieptEnabled(){
        return (!StringUtils.isEmpty(inputQty)&& Integer.parseInt(inputQty) >0 && Integer.parseInt(inputQty) <= position.getAvailQuantity() );
    }

    public void recieptClicked(View v){
        if (Integer.parseInt(inputQty) > position.getAvailQuantity()) {
            getViewOptional().showSnakeBar("Введенное количество больше доступного в заказе");
            return;
        }
        showLoader();
        compositeDisposable.add(
                netApi.recieptOrder(new RecieptOrderRequest(Integer.parseInt(position.getOrderName()), position.getLineNumber(),
                        getSelectedLot(), Integer.parseInt(inputQty) ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processRecieptResponse, this::processError)
        );
    }

    private void processRecieptResponse(RecieptOrderResponse recieptOrderResponse) {
        hideLoader();
        if (recieptOrderResponse.data.resultCode == 0){
            position.setAvailQuantity(position.getAvailQuantity() - recieptOrderResponse.data.qtyRecieved);
            notifyChange();
        } else {
            getViewOptional().showSnakeBar(recieptOrderResponse.data.resultMessage);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        StateHelperRecieptProcessingVM.onSaveInstanceState(this, bundle);
    }
}
