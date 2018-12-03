package com.ranok.ui.move_lpn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioGroup;

import com.ranok.R;
import com.ranok.network.request.MoveLpnRequest;
import com.ranok.network.request.SplitAndMoveRequest;
import com.ranok.network.request.SplitLpnRequest;
import com.ranok.network.response.LpnOperationResponse;
import com.ranok.ui.base.BaseViewModel;
import com.ranok.ui.base.search_widget.SearchLpnWidgetVM;
import com.ranok.ui.base.search_widget.SearchPlaceWidgetVM;
import com.ranok.utils.StringUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ranok.annotation.State;

import static com.ranok.ui.move_lpn.MoveLpnFragment.LPN;
import static com.ranok.ui.move_lpn.MoveLpnFragment.SPLIT;


public class MoveLpnVM extends BaseViewModel<MoveLpnIView> {
    private static final String SEARCH_WIDGET_SOURCE_PLN_TAG = "MoveLpnVMSourceLpn",
            SEARCH_WIDGET_TARGET_PLN_TAG = "MoveLpnVMTargetLpn",
            SEARCH_WIDGET_TARGET_PLACE_TAG = "MoveLpnVMTargetPlace";



    private SearchLpnWidgetVM searchSourceLpnVM, searchAimLpnVM;
    private SearchPlaceWidgetVM searchAimPlaceVM;
    private MoveLpnModel model = new MoveLpnModel();

    @State
    SplitLpnRequest splitLpnRequest;

    @State
    boolean transaction;

    public int isSimple() {
        return transaction ? View.GONE : View.VISIBLE;
    }

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

        String lpn ="";
        if (arguments != null) {
            lpn = StringUtils.formatFromLpn(arguments.getString(LPN));
            if (arguments.containsKey(SPLIT)){
                splitLpnRequest = arguments.getParcelable(SPLIT);
                transaction = true;
            }
        }

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

        if (lpn != null && !lpn.isEmpty() && !transaction){
            searchSourceLpnVM.onTextChanged(lpn,0,0,0);
        }
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
            if (model.getArrRbAim()[0] == id) model.setSelectedAim(0);
            if (model.getArrRbAim()[1] == id) model.setSelectedAim(1);
            if (model.getArrRbAim()[2] == id) model.setSelectedAim(2);
            setupUI();
        }
        if (radioGroup.getId() == R.id.rGroupType) {
            if (model.getArrRbType()[0] == id) model.setSelectedType(0);
            else if (model.getArrRbType()[1] == id) model.setSelectedType(1);
        }

    }

    public void onClick(View v){
        if (model.getSelectedAim() == 2 && StringUtils.isEmpty( model.getNewLpnPlace() )) {
            getViewOptional().showInputPlace("Номер ячейки");
            return;
        }
        showLoader();
        String sourceLpn,targetLpn = null, targetPlaceAddress, moveType;
        sourceLpn = StringUtils.formatToLpn(searchSourceLpnVM.getInputText());
        if (model.getSelectedAim() == 0 ) {
            targetLpn = StringUtils.formatToLpn(searchAimLpnVM.getInputText());
            targetPlaceAddress = model.getNewLpnPlace();
        } else  if (model.getSelectedAim() == 1 ){
            targetPlaceAddress = searchAimPlaceVM.getInputText();
        } else {
            targetPlaceAddress = model.getNewLpnPlace();
        }
        moveType = model.getSelectedType() == 0 ? "full_lpn" : "lpn_content";

        if (!transaction) {
            compositeDisposable.add( //String sourceLpn, String targetLpn, String targetPlaceAddress, String moveType
                    netApi.moveLpn(new MoveLpnRequest(sourceLpn, targetLpn, targetPlaceAddress, moveType, model.getSelectedAim()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::processResponse, this::processError)
            );
        } else if (splitLpnRequest != null) {
            MoveLpnRequest moveLpnRequest = new MoveLpnRequest(sourceLpn, targetLpn, targetPlaceAddress, moveType, model.getSelectedAim());
            compositeDisposable.add( //String sourceLpn, String targetLpn, String targetPlaceAddress, String moveType
                    netApi.splitAndMoveLpn(new SplitAndMoveRequest(splitLpnRequest, moveLpnRequest))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::processResponse, this::processError)
            );
        }

    }

    private void processResponse(LpnOperationResponse response) {
        hideLoader();
        model.setNewLpnPlace(null);
        if (response.data.resultCode == 0) {
            String msg = "Операция прошла успешно";
            if (response.data.newLpnCode != null) msg += "Новый НЗ - " + response.data.newLpnCode;
            showToast(msg);
            getViewOptional().closeScreen();
        } else if (response.data.resultCode == -1) {
            getViewOptional().showInputPlace("Номер ячейки");
        }
        else {
            getViewOptional().showSnakeBar(response.data.resultMessage);
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

