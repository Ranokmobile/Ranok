package com.ranok.ui.info_position;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.R;
import com.ranok.ui.base.BaseViewModel;
import com.ranok.ui.base.search_widget.SearchPositionWidgetVM;
import com.ranok.ui.base.search_widget.SearchWidgetCallbacks;


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
        switch (v.getId()) {
            case R.id.ibSearch : startSearch();
            break;
            case R.id.ibBarcode : getViewOptional().startScanBarcode();
            break;
        }
    }

    private void startSearch() {

    }

    public void gotBarcode(String barcode){
        searchVM.onTextChanged(barcode,0,0,0);
    }
}
