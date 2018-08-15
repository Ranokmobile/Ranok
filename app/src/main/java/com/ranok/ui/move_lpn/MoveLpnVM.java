package com.ranok.ui.move_lpn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioGroup;

import com.ranok.R;
import com.ranok.ui.base.BaseViewModel;
import com.ranok.ui.base.search_widget.SearchLpnWidgetVM;
import com.ranok.ui.base.search_widget.SearchPlaceWidgetVM;


public class MoveLpnVM extends BaseViewModel<MoveLpnIView> {
    private static final String SEARCH_WIDGET_SOURCE_PLN_TAG = "MoveLpnVMSourceLpn",
            SEARCH_WIDGET_TARGET_PLN_TAG = "MoveLpnVMTargetLpn",
            SEARCH_WIDGET_TARGET_PLACE_TAG = "MoveLpnVMTargetPlace";

    private SearchLpnWidgetVM searchSourceLpnVM, searchAimLpnVM;
    private SearchPlaceWidgetVM searchAimPlaceVM;
    private MoveLpnModel model = new MoveLpnModel();



   // private boolean intoLpn, intoPlace, fullLpn, onlyContent;


    public SearchLpnWidgetVM getSearchSourceLpnVM() {
        return searchSourceLpnVM;
    }

    public SearchLpnWidgetVM getSearchAimLpnVM() {
        return searchAimLpnVM;
    }

    public SearchPlaceWidgetVM getSearchAimPlaceVM() {
        return searchAimPlaceVM;
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        searchSourceLpnVM = new SearchLpnWidgetVM(SEARCH_WIDGET_SOURCE_PLN_TAG, v -> {
            hideKeyboard();
            getViewOptional().startScanBarcode(SearchWidgets.SOURCE_LPN);
        }, false);
        searchAimLpnVM = new SearchLpnWidgetVM(SEARCH_WIDGET_TARGET_PLN_TAG, v -> {
            hideKeyboard();
            getViewOptional().startScanBarcode(SearchWidgets.TARGET_LPN);
        }, false);
        searchAimPlaceVM = new SearchPlaceWidgetVM(SEARCH_WIDGET_TARGET_PLACE_TAG, v -> {
            hideKeyboard();
            getViewOptional().startScanBarcode(SearchWidgets.TARGET_PLACE);
        }, false);
        setupUI();
    }

    private void setupUI() {
        notifyChange();
    }

    public int isTargetLpnVisible(){
        return model.getSelectedAim()==0 ? View.VISIBLE : View.GONE;
    }
    public int isTargetPlaceVisible(){
        return model.getSelectedAim()==1 ? View.VISIBLE : View.GONE;
    }


    public MoveLpnModel getModel() {
        return model;
    }

    public void gotBarcode(String barcode, SearchWidgets item){
        switch (item){
            case SOURCE_LPN: searchSourceLpnVM.onTextChanged(barcode,0,0,0);
            break;
            case TARGET_LPN: searchAimLpnVM.onTextChanged(barcode,0,0,0);
            break;
            case TARGET_PLACE: searchAimPlaceVM.onTextChanged(barcode,0,0,0);
            break;
        }
        //searchVM.onTextChanged(barcode,0,0,0);

    }

    public void onRadioGroupChanged(RadioGroup radioGroup, int id) {
        if (radioGroup.getId() == R.id.rGroupAim) {
            if (model.getArrRbAim()[0] == id) model.setSelectedAim(0); else model.setSelectedAim(1);
            setupUI();
        }
        if (radioGroup.getId() == R.id.rGroupType) {
            if (model.getArrRbType()[0] == id) model.setSelectedType(0); else model.setSelectedType(1);
        }
    }


    enum SearchWidgets {
        SOURCE_LPN(1), TARGET_LPN(2), TARGET_PLACE(3), UNKNOWN(-1);
        int tag;

        SearchWidgets(int tag) {
            this.tag = tag;
        }

        public static SearchWidgets getByTag(int method){
            for(SearchWidgets e : SearchWidgets.values()){
                if(method == e.tag) return e;
            }
            return UNKNOWN;
        }
    }
}


/*
    @Bindable
    public boolean isIntoLpn() {
        return intoLpn;
    }

    public void setIntoLpn(boolean intoLpn) {
        this.intoLpn = intoLpn;
        notifyPropertyChanged(BR.intoLpn);
    }

    @Bindable
    public boolean isIntoPlace() {
        return intoPlace;
    }

    public void setIntoPlace(boolean intoPlace) {
        this.intoPlace = intoPlace;
        notifyPropertyChanged(BR.intoPlace);
    }

    @Bindable
    public boolean isFullLpn() {
        return fullLpn;
    }

    public void setFullLpn(boolean fullLpn) {
        this.fullLpn = fullLpn;
        notifyPropertyChanged(BR.fullLpn);
    }

    @Bindable
    public boolean isOnlyContent() {
        return onlyContent;
    }

    public void setOnlyContent(boolean onlyContent) {
        this.onlyContent = onlyContent;
        notifyPropertyChanged(BR.onlyContent);
    }

 */