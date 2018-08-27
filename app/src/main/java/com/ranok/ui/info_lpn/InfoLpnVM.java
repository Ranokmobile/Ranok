package com.ranok.ui.info_lpn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.orhanobut.hawk.Hawk;
import com.ranok.BR;
import com.ranok.R;
import com.ranok.adapters.RecyclerBindingAdapter;
import com.ranok.network.models.LpnInfoModel;
import com.ranok.network.models.PlaceInfoModel;
import com.ranok.network.request.CodeRequest;
import com.ranok.network.response.LpnInfoResponse;
import com.ranok.network.response.LpnInfoResponseData;
import com.ranok.ui.base.BaseViewModel;
import com.ranok.ui.base.search_widget.SearchLpnWidgetVM;
import com.ranok.ui.base.search_widget.SearchWidgetCallbacks;
import com.ranok.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class InfoLpnVM extends BaseViewModel<InfoLpnIView> implements SearchWidgetCallbacks {
    private static final String SEARCH_WIDGET_TAG = "InfoLpnVM";

    private LpnInfoModel lpnInfoModel;
    private ArrayList<PlaceInfoModel> listLpnPositions;

    private SearchLpnWidgetVM searchVM;

    private RecyclerBindingAdapter<PlaceInfoModel> adapter
            = new RecyclerBindingAdapter<>(R.layout.item_position_lpn, BR.viewModel, new ArrayList<>());

    public LpnInfoModel getLpnInfoModel() {
        return lpnInfoModel;
    }

    public SearchLpnWidgetVM getSearchVM() {
        return searchVM;
    }

    public RecyclerBindingAdapter<PlaceInfoModel> getAdapter() {
        return adapter;
    }

    public List<PlaceInfoModel> getListLpnPositions() {
        return listLpnPositions;
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        searchVM = new SearchLpnWidgetVM(SEARCH_WIDGET_TAG, this);

        LpnInfoResponseData data = Hawk.get("LPN_DATA");
        if (data != null) {
            this.lpnInfoModel = data.getLpnInfoModel();
            this.listLpnPositions = data.getListLpnPositions();
            if(listLpnPositions != null) {
                adapter.setItems(listLpnPositions);
            } else {
                adapter.setItems(new ArrayList<>());
            }
            notifyChange();
        }
        String s = Hawk.get("LPN_TEXT");
        if (s!=null) searchVM.onTextChanged(s,0,0,0);

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
        String searchStr = StringUtils.formatToLpn(searchVM.getInputText());
        searchByCode(searchStr);
    }

    private void searchByCode(String code) {
        showLoader();
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

    public boolean isRvVisible(){
        return (listLpnPositions != null && listLpnPositions.size()>0);
    }


    private void processResponse(LpnInfoResponse response) {
        hideLoader();
        Hawk.put("LPN_DATA", response.data);
        Hawk.put("LPN_TEXT", searchVM.getInputText());
        this.lpnInfoModel = response.data.getLpnInfoModel();
        this.listLpnPositions = response.data.getListLpnPositions();
        if(listLpnPositions != null) {
            adapter.setItems(listLpnPositions);
        } else {
            adapter.setItems(new ArrayList<>());
        }
        notifyChange();
    }

    public void moveClick(View v){
        getViewOptional().showMove(searchVM.getInputText());
    }

    public void splitClick(View v){
        getViewOptional().showSplit(searchVM.getInputText(),listLpnPositions );
    }

    public void unpackClick(View v){
        getViewOptional().showUnpack(searchVM.getInputText(),listLpnPositions );
    }

    public void printClick(View v){
        getViewOptional().showPrint(searchVM.getInputText(),listLpnPositions );
    }



}
