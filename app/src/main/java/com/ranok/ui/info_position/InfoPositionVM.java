package com.ranok.ui.info_position;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.R;
import com.ranok.network.request.BarcodeRequest;
import com.ranok.network.request.PositionCodeRequest;
import com.ranok.network.response.PositionInfoByBarcodeResponse;
import com.ranok.rx_bus.RxPosInfo;
import com.ranok.ui.base.BaseViewModel;
import com.ranok.ui.base.search_widget.SearchPositionWidgetVM;
import com.ranok.ui.base.search_widget.SearchWidgetCallbacks;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.ranok.ui.base.search_widget.SearchPositionWidgetVM.ItemType.ITEM_BARCODE;
import static com.ranok.ui.base.search_widget.SearchPositionWidgetVM.ItemType.PACK_BARCODE;
import static com.ranok.ui.base.search_widget.SearchPositionWidgetVM.ItemType.PIECE_BARCODE;


public class InfoPositionVM extends BaseViewModel<InfoPositionIView> implements SearchWidgetCallbacks {
    private static final String SEARCH_WIDGET_TAG = "InfoPositionVM";
    private SearchPositionWidgetVM searchVM;

    public SearchPositionWidgetVM getSearchVM() {
        return searchVM;
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        searchVM = new SearchPositionWidgetVM(SEARCH_WIDGET_TAG, this);
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

    private void startSearch() {
        SearchPositionWidgetVM.ItemType itemType;
        itemType = searchVM.getItemType();
        switch (itemType) {
            case ITEM_CODE: searchByCode(searchVM.getInputText());
                break;
            case ITEM_BARCODE: searchByBarcode(ITEM_BARCODE.type,"000000");
                break;
            case PACK_BARCODE: searchByBarcode(PACK_BARCODE.type, "000000");
                break;
            case PIECE_BARCODE: searchByBarcode(PIECE_BARCODE.type, "0");
                break;
            case UNKNOWN: break;
        }
    }

    private void searchByBarcode(String type, String prefix) {
        compositeDisposable.add(
                netApi.getPositionsByBarcode(
                            new BarcodeRequest(prefix+searchVM.getInputText(),type)
                         )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::processPositionsByBarcodeResponse, this::processError)
        );
    }

    public void searchByCode(String code) {
        compositeDisposable.add(
                netApi.getPositionsByCode(new PositionCodeRequest(code))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::processPositionsByCodeResponse, this::processError)
        );
    }

    private void processPositionsByCodeResponse(PositionInfoByBarcodeResponse response) {
        RxPosInfo.getInstance().sendRFIDData(response.data);
    }


    private void processPositionsByBarcodeResponse(PositionInfoByBarcodeResponse response) {
        RxPosInfo.getInstance().sendRFIDData(response.data);
        if (response.data.getPositionList() == null){
            getViewOptional().showSnakeBar("Данные ненайдены");
        } else  if (response.data.getPositionList().size()>1) { //нужно выбирать из нескольких позиций
            getViewOptional().showSelectPositionDialog(response.data.getPositionList());
        }
    }

    public void gotBarcode(String barcode){
        searchVM.onTextChanged(barcode,0,0,0);
        startSearch();
    }
}
