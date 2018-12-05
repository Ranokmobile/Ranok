package com.ranok.ui.info_position;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.orhanobut.hawk.Hawk;
import com.ranok.R;
import com.ranok.network.request.BarcodeRequest;
import com.ranok.network.request.CodeRequest;
import com.ranok.network.response.PositionInfoByBarcodeData;
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

    private PositionInfoByBarcodeData data;

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        searchVM = new SearchPositionWidgetVM(SEARCH_WIDGET_TAG, this);

        //bundle.putString("code", code);
        if (arguments!= null && arguments.containsKey("code")) {
            String s = arguments.getString("code");
            if (s!=null) searchVM.onTextChanged(s,0,0,0);
        } else {
            data = Hawk.get("POSITION");
            if (data != null) {
                RxPosInfo.getInstance().sendData(data);
                getViewOptional().updatePager(data.getPositionInReceiptList() != null
                        && data.getPositionInReceiptList().size() > 0);
            }
            String s = Hawk.get("POSITION_TEXT");
            if (s != null) searchVM.onTextChanged(s, 0, 0, 0);
        }
    }

    public int getPagesCount(){
        if (data != null) {
            return (data.getPositionInReceiptList() != null
                    && data.getPositionInReceiptList().size()>0) ? 3 : 2;
        } else return 2;
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

    public void startSearch() {
        SearchPositionWidgetVM.ItemType itemType;
        itemType = searchVM.getItemType();
        if (itemType != null)
        switch (itemType) {
            case ITEM_CODE: searchByCode(searchVM.getInputText());
                break;
            case ITEM_BARCODE: searchByBarcode(ITEM_BARCODE.type,"000000");
                break;
            case PACK_BARCODE: searchByBarcode(PACK_BARCODE.type, "000000");
                break;
            case PIECE_BARCODE: searchByBarcode(PIECE_BARCODE.type, "0");
                break;
            case UNKNOWN: showToast("Некорректный ввод");
                break;
        }
    }

    private void searchByBarcode(String type, String prefix) {
        showLoader();
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
        showLoader();
        compositeDisposable.add(
                netApi.getPositionsByCode(new CodeRequest(code,""))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::processPositionsByCodeResponse, this::processError)
        );
    }

    private void processPositionsByCodeResponse(PositionInfoByBarcodeResponse response) {
        hideLoader();
        Hawk.put("POSITION", response.data);
        Hawk.put("POSITION_TEXT", searchVM.getInputText());
        if (response.data.getPositionInReceiptList() != null
                && response.data.getPositionInReceiptList().size()>0) {
            getViewOptional().updatePager(true);
        } else {
            getViewOptional().updatePager(false);
        }
        RxPosInfo.getInstance().sendData(response.data);
    }


    private void processPositionsByBarcodeResponse(PositionInfoByBarcodeResponse response) {
        hideLoader();
        Hawk.put("POSITION", response.data);
        Hawk.put("POSITION_TEXT", searchVM.getInputText());
        RxPosInfo.getInstance().sendData(response.data);
        if (response.data.getPositionList() == null){
            getViewOptional().showSnakeBar("Данные не найдены");
        } else  if (response.data.getPositionList().size()>1) { //нужно выбирать из нескольких позиций
            getViewOptional().showSelectPositionDialog(response.data.getPositionList());
        }
    }

    public void gotBarcode(String barcode){
        searchVM.onTextChanged(barcode,0,0,0);
        startSearch();
    }
}
