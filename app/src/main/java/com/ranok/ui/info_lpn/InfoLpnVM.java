package com.ranok.ui.info_lpn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.R;
import com.ranok.network.models.LpnInfoModel;
import com.ranok.network.request.CodeRequest;
import com.ranok.network.response.LpnInfoResponse;
import com.ranok.ui.base.BaseViewModel;
import com.ranok.ui.base.search_widget.SearchLpnWidgetVM;
import com.ranok.ui.base.search_widget.SearchWidgetCallbacks;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class InfoLpnVM extends BaseViewModel<InfoLpnIView> implements SearchWidgetCallbacks {
    private static final String SEARCH_WIDGET_TAG = "InfoLpnVM";

    private LpnInfoModel lpnInfoModel;

    private SearchLpnWidgetVM searchVM;

    public LpnInfoModel getLpnInfoModel() {
        return lpnInfoModel;
    }

    public SearchLpnWidgetVM getSearchVM() {
        return searchVM;
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        searchVM = new SearchLpnWidgetVM(SEARCH_WIDGET_TAG, this);
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
        if (searchVM.getInputText() == null) return;
        String searchStr = "L";
        int inputLength = searchVM.getInputText().length();
        for (int i = inputLength; i < 9; i++) searchStr+="0";
        searchStr = searchStr + searchVM.getInputText();
        searchByCode(searchStr);
    }

    public void searchByCode(String code) {
        compositeDisposable.add(
                netApi.getLpnByCode(new CodeRequest(code))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::processResponse, this::processError)
        );
    }

    public void gotBarcode(String barcode){
        searchVM.onTextChanged(barcode,0,0,0);
        startSearch();
    }

    public boolean isInfoVisible(){
        return (lpnInfoModel != null);
    }

    private void processResponse(LpnInfoResponse lpnInfoResponse) {
        this.lpnInfoModel = lpnInfoResponse.data.getLpnInfoModel();
        notifyChange();
    }

}
