package com.ranok.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.ActivityLoginBinding;
import com.ranok.ui.base.BaseActivity;
import com.ranok.ui.main.MainActivity;

import ranok.mvvm.binding.ViewModelBindingConfig;

public class LoginActivity extends BaseActivity<LoginActivityIView, LoginActivityVM, ActivityLoginBinding>
        implements LoginActivityIView  {

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.activity_login, BR.viewModel, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setModelView(this);
    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}

