package com.ranok.ui.reciept;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.R;
import com.ranok.network.request.AcceptListRequest;
import com.ranok.network.response.AcceptListResponse;
import com.ranok.ui.base.BaseViewModel;
import com.ranok.ui.base.search_widget.SearchLpnWidgetVM;
import com.ranok.ui.base.search_widget.SearchWidgetCallbacks;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ranok.annotation.State;


public class CheckRecieptIVM extends BaseViewModel<CheckRecieptIView>
        implements SearchWidgetCallbacks {
    public static final String SEARCH_WIDGET_SOURCE_LPN_TAG =  "CheckRecieptIVM";

    QualityCode qualityCode;

    List<String> qualities;

    @State
    String lpn;

    private SearchLpnWidgetVM searchSourceLpnVM;

    public SearchLpnWidgetVM getSearchSourceLpnVM() {
        return searchSourceLpnVM;
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);

        compositeDisposable.add(Observable.fromIterable(Arrays.asList(QualityCode.values()) )
                .map(i->i.label).toList().subscribe(strings -> qualities = strings)
        );

        searchSourceLpnVM = new SearchLpnWidgetVM(SEARCH_WIDGET_SOURCE_LPN_TAG, this);
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
        compositeDisposable.add(
          netApi.getAcceptList(new AcceptListRequest(searchSourceLpnVM.getInputText()))
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(this::processSearchResonse, this::processError)
        );
    }

    private void processSearchResonse(AcceptListResponse acceptListResponse) {

    }
}
