package com.ranok.ui.reciept;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.ranok.R;
import com.ranok.network.models.RecieptListModel;
import com.ranok.network.request.RecieptListRequest;
import com.ranok.network.response.RecieptListResponse;
import com.ranok.ui.base.BaseViewModel;
import com.ranok.ui.base.search_widget.SearchOrderWidgetVM;
import com.ranok.ui.base.search_widget.SearchWidgetCallbacks;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ranok.annotation.State;


public class RecieptChooseOrderVM extends BaseViewModel<RecieptChooseOrderIView>
        implements SearchView.OnQueryTextListener, SearchWidgetCallbacks{

    public interface RecieptChooseOrderItemSelected{
        void onClick(RecieptListModel model);

    }

    private static final String SEARCH_WIDGET_TAG = "RecieptChooseOrderVM";

    @State
    String filterText;


    private SearchOrderWidgetVM searchVM;
    private List<RecieptListModel> adapterItems = new ArrayList<>();
    private RecieptChooseOrderItemSelected callbacks = RecieptChooseOrderVM.this::itemClicked;
    private RecieptChooseOrderAdapter adapter = new RecieptChooseOrderAdapter(adapterItems, callbacks);

    public RecieptChooseOrderAdapter getAdapter() {
        return adapter;
    }

    public SearchOrderWidgetVM getSearchVM() {
        return searchVM;
    }

    private SearchView.OnQueryTextListener listener;

    public SearchView.OnQueryTextListener getListener() {
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        StateHelperRecieptChooseOrderVM.onRestoreInstanceState(this, savedInstanceState);
        adapter.getFilter().filter(filterText);
        searchVM = new SearchOrderWidgetVM(SEARCH_WIDGET_TAG, this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        StateHelperRecieptChooseOrderVM.onSaveInstanceState(this, bundle);
    }

    @Override
    public void onClickWidgetSearch(View v) {
        hideKeyboard();
        switch (v.getId()) {
            case R.id.ibSearch:
                startSearch();
                break;
        }
    }

    public void startSearch() {
        showLoader();
        compositeDisposable.add(
          netApi.getRecieptList(new RecieptListRequest(searchVM.getInputText()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::processResponse, this::processError)
        );
    }

    private void processResponse(RecieptListResponse response) {
        hideLoader();
        List<RecieptListModel> newList = new ArrayList<>();
        if (response.data.getRecieptList() != null) newList = response.data.getRecieptList();
        adapter.setItems(newList);
        adapter.getFilter().filter(filterText);
    }

    private void itemClicked(RecieptListModel model) {
        getViewOptional().showProcessing(model);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }
}
