package com.ranok.ui.reciept;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;

import com.ranok.ui.base.BaseViewModel;
import com.ranok.ui.base.search_widget.SearchOrderWidgetVM;


public class RecieptChooseOrderVM extends BaseViewModel<RecieptChooseOrderIView>
        implements SearchView.OnQueryTextListener {

    private SearchOrderWidgetVM searchVM;

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
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
