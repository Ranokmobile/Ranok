package com.ranok.ui.info_place;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.orhanobut.hawk.Hawk;
import com.ranok.BR;
import com.ranok.R;
import com.ranok.adapters.RecyclerBindingAdapter;
import com.ranok.network.models.PlaceInfoModel;
import com.ranok.network.request.PlaceRequest;
import com.ranok.network.response.PlaceInfoData;
import com.ranok.network.response.PlaceInfoResponse;
import com.ranok.ui.base.BaseViewModel;
import com.ranok.ui.base.search_widget.SearchPlaceWidgetVM;
import com.ranok.ui.base.search_widget.SearchWidgetCallbacks;
import java.util.ArrayList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class InfoPlaceVM extends BaseViewModel<InfoPlaceIView> implements SearchWidgetCallbacks ,
        RecyclerBindingAdapter.OnItemClickListener<PlaceInfoModel> {
    private static final String SEARCH_WIDGET_TAG = "InfoPlaceVM";

    private PlaceInfoData data = new PlaceInfoData();

    private RecyclerBindingAdapter<PlaceInfoModel> adapter
            = new RecyclerBindingAdapter<>(R.layout.item_place_items, BR.viewModel, new ArrayList<>());

    public RecyclerBindingAdapter<PlaceInfoModel> getAdapter() {
        return adapter;
    }

    private SearchPlaceWidgetVM searchVM;

    public SearchPlaceWidgetVM getSearchVM() {
        return searchVM;
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        searchVM = new SearchPlaceWidgetVM(SEARCH_WIDGET_TAG, this);
        adapter.setOnItemClickListener(this);
        data = Hawk.get("PLACE_DATA");
        if (data != null) {
            if(data.getPlaceInfoList() == null) {
                adapter.setItems(new ArrayList<>());
            } else{
                adapter.setItems(data.getPlaceInfoList());
            }
            notifyChange();
        }
        String s = Hawk.get("PLACE_TEXT");
        if (s!=null) searchVM.onTextChanged(s,0,0,0);
    }

    @Override
    public void onItemClick(int position, PlaceInfoModel item) {
        getViewOptional().showMenu(item);
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
        searchVM.onTextChanged(barcode,0,0,0);
        startSearch();
    }

    public void startSearch() {
        if (searchVM.getInputText() == null) return;
        String searchStr = searchVM.getInputText();
        String[] sArr = searchStr.split("\\.");

        searchByCode(sArr[0], sArr[1], sArr[2], sArr[3]);
    }

    public void searchByCode(String rack, String space, String floor, String block) {
        showLoader();
        compositeDisposable.add(
                netApi.getPlaceItemsByCode(new PlaceRequest(rack, space, floor, block))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::processResponse, this::processError)
        );
    }

    public boolean isDataPresent(){
        if (data.getPlaceInfoList() == null) return false;
        if (data.getPlaceInfoList().size() == 0) return false;
        return true;
    }

    private void processResponse(PlaceInfoResponse placeInfoResponse) {
        hideLoader();
        Hawk.put("PLACE_DATA", placeInfoResponse.data);
        Hawk.put("PLACE_TEXT", searchVM.getInputText());
        data = placeInfoResponse.data;
        notifyChange();
        if(data.getPlaceInfoList() == null) {
            adapter.setItems(new ArrayList<>());
        } else{
            adapter.setItems(data.getPlaceInfoList());
        }
    }


}
