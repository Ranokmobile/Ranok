package com.ranok.ui.base;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ranok.R;
import com.ranok.RanokApp;
import com.ranok.network.NetApi;
import com.ranok.network.response.BaseResponse;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
import ranok.mvvm.AbstractViewModel;
import retrofit2.HttpException;

public abstract class BaseViewModel<T extends BaseIView> extends AbstractViewModel<T> {

    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected NetApi netApi = RanokApp.getApp().getNetApi();


    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, Boolean value) {
        if (value == null) view.setVisibility(View.GONE);
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    protected void showLoader(){
        getViewOptional().showLoader(hashCode());
    }

    protected void hideLoader(){
        getViewOptional().hideLoader(hashCode());
    }

    protected void processError(Throwable throwable) {
        hideLoader();
        int errorCode;
        String errorBody;

        if (throwable.getMessage() != null) {
            Log.d("error", throwable.getMessage());
        }

        if (throwable instanceof ConnectException) {
            getViewOptional().showSnakeBar(R.string.cant_connect_to_server);
        }

        if (throwable instanceof SocketTimeoutException) {
            getViewOptional().showSnakeBar(R.string.cant_connect_to_server);
        }

        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            if (httpException.response() != null) {
                errorCode = httpException.response().code();

                if (errorCode == 401) {
                    showToast(RanokApp.getApp().getString(R.string.session_expired));
                    getViewOptional().gotoLogin();
                    return;
                }

                if (httpException.response().errorBody() != null && errorCode != 500) {
                    try {
                        ResponseBody responseBody = httpException.response().errorBody();
                        if (responseBody != null) {
                            errorBody = responseBody.string();
                            BaseResponse baseResponse = new Gson().fromJson(errorBody, BaseResponse.class);
                            getViewOptional().showSnakeBar(baseResponse.message);
                            showToast(errorBody);
                            Log.d("error", errorBody);
                        }
                    } catch (Exception e1) {
                        Log.d("JSON", "бодяга пришла");
                    }
                } else {
                    showToast("Ошибка обработки запроса");
                }
            }
        }

    }

    protected void showToast(String s) {
        Toast.makeText(RanokApp.getApp(), s, Toast.LENGTH_SHORT).show();
    }

    protected void hideKeyboard(){
        getViewOptional().hideKeyboard();
    }

    @Override
    public void onDestroy() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) compositeDisposable.dispose();
    }
}
