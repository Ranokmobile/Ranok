package com.ranok.ui.base;

import android.databinding.Bindable;

import io.reactivex.disposables.CompositeDisposable;
import ranok.mvvm.AbstractViewModel;
import com.ranok.BR;
import com.ranok.RanokApp;
import com.ranok.network.NetApi;

public abstract class BaseViewModel<T extends BaseIView> extends AbstractViewModel<T> {

    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected NetApi netApi = RanokApp.getApp().getNetApi();
    private boolean loading;

    @Bindable
    public boolean isLoading() {
        return loading;
    }

    private void setLoading(boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }

    protected void showLoading() {
        setLoading(true);
    }

    protected void hideLoading() {
        setLoading(false);
    }

    protected void processError(Throwable throwable) {
        hideLoading();
    }

    @Override
    public void onDestroy() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) compositeDisposable.dispose();
    }
}
