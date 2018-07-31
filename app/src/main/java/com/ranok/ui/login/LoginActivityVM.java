package com.ranok.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.orhanobut.hawk.Hawk;
import com.ranok.network.request.LoginRequest;
import com.ranok.network.response.LoginResponse;
import com.ranok.ui.base.BaseViewModel;
import com.ranok.utils.Consts;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ranok.annotation.State;

public class LoginActivityVM extends BaseViewModel<LoginActivityIView> {

    @State
    String login;

    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        StateHelperLoginActivityVM.onRestoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        StateHelperLoginActivityVM.onSaveInstanceState(this, bundle);
    }

    public void onLoginClick(View v){
        showLoader();
        compositeDisposable.add(netApi.login(new LoginRequest(login, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoginSuccess, this::processError)
        );
    }

    private void onLoginSuccess(LoginResponse s) {
        //Hawk.put(Consts.TOKEN, s.token);
        Hawk.put(Consts.TOKEN, "tttt");
        hideLoader();
        getViewOptional().startMainActivity();
    }
}
