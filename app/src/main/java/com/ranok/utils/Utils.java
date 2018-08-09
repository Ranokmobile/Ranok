package com.ranok.utils;

import io.reactivex.disposables.CompositeDisposable;

public class Utils {
    public static void dismissDisposable(CompositeDisposable compositeDisposable){
        if (compositeDisposable != null && !compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }
}
