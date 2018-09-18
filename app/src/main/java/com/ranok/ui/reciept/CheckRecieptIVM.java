package com.ranok.ui.reciept;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.R;
import com.ranok.network.models.AcceptListModel;
import com.ranok.network.request.AcceptListRequest;
import com.ranok.network.request.AcceptOrderRequest;
import com.ranok.network.response.AcceptListResponse;
import com.ranok.network.response.AcceptOrderResponse;
import com.ranok.ui.base.BaseViewModel;
import com.ranok.ui.base.search_widget.SearchLpnWidgetVM;
import com.ranok.ui.base.search_widget.SearchWidgetCallbacks;
import com.ranok.utils.StringUtils;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ranok.annotation.State;


public class CheckRecieptIVM extends BaseViewModel<CheckRecieptIView>
        implements SearchWidgetCallbacks {
    public static final String SEARCH_WIDGET_SOURCE_LPN_TAG =  "CheckRecieptIVM";

    @State
    AcceptListModel model;


    QualityCode qualityCode = QualityCode.UNKNOWN;

    List<String> qualities;

    @State
    String inputQty;

    @State
    String lpn;

    public QualityCode getQualityCode() {
        return qualityCode;
    }

    public List<String> getQualities() {
        return qualities;
    }

    public AcceptListModel getModel() {
        return model;
    }

    public String getInputQty() {
        return inputQty;
    }

    public void setInputQty(String inputQty) {
        this.inputQty = inputQty;
    }

    private SearchLpnWidgetVM searchSourceLpnVM;

    public SearchLpnWidgetVM getSearchSourceLpnVM() {
        return searchSourceLpnVM;
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        StateHelperCheckRecieptIVM.onRestoreInstanceState(this, savedInstanceState);

        compositeDisposable.add(Observable.fromIterable(Arrays.asList(QualityCode.values()) )
                .map(i->i.label).toList().subscribe(strings -> qualities = strings)
        );
        searchSourceLpnVM = new SearchLpnWidgetVM(SEARCH_WIDGET_SOURCE_LPN_TAG, this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        StateHelperCheckRecieptIVM.onSaveInstanceState(this, bundle);
    }

    public void spinnerItemSelected(int i){
        qualityCode = QualityCode.getByNpp(i);
    }

    @Override
    public void onClickWidgetSearch(View v) {
        hideKeyboard();
        switch (v.getId()) {
            case R.id.ibSearch : startSearch();
                break;
            case R.id.ibBarcode : getViewOptional().startScanBarcode();
                break;
        }
    }

    public void gotBarcode(String barcode){
        searchSourceLpnVM.onTextChanged(barcode, 0,0,0);
    }

    public void startSearch() {
        showLoader();
        lpn = searchSourceLpnVM.getInputText();
        compositeDisposable.add(
          netApi.getAcceptList(new AcceptListRequest(StringUtils.formatToLpn(searchSourceLpnVM.getInputText())))
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(this::processSearchResponse, this::processError)
        );
    }

    public boolean isModelAvailable(){
        return model != null;
    }

    private void processSearchResponse(AcceptListResponse acceptListResponse) {
        hideLoader();
        inputQty = null;
        spinnerItemSelected(7);
        getViewOptional().setupSpinner();
        if (acceptListResponse.data.getAcceptList() == null
                || acceptListResponse.data.getAcceptList().size()==0) {
            model =null;
        } else {
            model = acceptListResponse.data.getAcceptList().get(0);
        }
        notifyChange();
    }

    public void checkClicked(View v){
        if (StringUtils.isEmpty(inputQty) || (Integer.valueOf(inputQty)<=0) || (Integer.valueOf(inputQty)>model.getQuantity() )){
            getViewOptional().showSnakeBar("Некорректное количество");
            return;
        }
        if (qualityCode == QualityCode.UNKNOWN){
            getViewOptional().showSnakeBar("Укажите параметр Качество");
            return;
        }
        showLoader();
        compositeDisposable.add(
                netApi.acceptOrder(new AcceptOrderRequest(StringUtils.formatToLpn(lpn) , model.getItemCode(), model.getLot(),
                        Integer.valueOf(inputQty), qualityCode.toString()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::processAcceptResponse, this::processError)
        );
    }

    private void processAcceptResponse(AcceptOrderResponse acceptOrderResponse) {
        hideLoader();
        if (acceptOrderResponse.data.resultCode == 0 || acceptOrderResponse.data.resultCode == 30){
            model.setQuantity(model.getQuantity() - acceptOrderResponse.data.qtyRecieved);
            spinnerItemSelected(7);
            inputQty = "";
            getViewOptional().setupSpinner();
            notifyChange();
            if (model.getQuantity() == 0) {
                getViewOptional().showPlacementDialog(model.getLpn());
            } else {
                model.setLpn(lpn);
                lpn = StringUtils.formatFromLpn(acceptOrderResponse.data.lpnRejected);
                searchSourceLpnVM.setInputText(lpn);
                getViewOptional().showSnakeBar("Сформирован НЗ "
                        + acceptOrderResponse.data.lpnRejected
                        + " c отклоненным кол-вом"
                );
            }
        } else {
            getViewOptional().showSnakeBar(acceptOrderResponse.data.resultMessage);
        }
    }
}
