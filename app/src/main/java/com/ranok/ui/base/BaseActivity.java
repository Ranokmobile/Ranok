package com.ranok.ui.base;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ranok.ui.dialogs.LoaderDialog;
import com.ranok.ui.login.LoginActivity;

import ranok.mvvm.base.ViewModelBaseActivity;


public abstract class BaseActivity<T extends BaseIView, R extends BaseViewModel<T>, B extends ViewDataBinding>
        extends ViewModelBaseActivity<T, R> implements BaseIView {

    protected B binding;
    protected R viewModel;

    protected SparseArray<LoaderDialog> loaderDialogs = new SparseArray<>();
    protected LoaderDialog loaderDialog;

    @Override
    public void showLoader(int hashCode) {
//            loaderDialogs.put(hashCode, new LoaderDialog(this));
//            loaderDialogs.get(hashCode).show();
        if (loaderDialog == null){
            loaderDialog  = new LoaderDialog(this);
            loaderDialog.show();
        }
    }

    @Override
    public void hideLoader(int hash) {
        if (loaderDialog != null) {
            if (loaderDialog.isShowing()) {
                loaderDialog.dismiss();
            }
            loaderDialog = null;
        }
//        if (loaderDialogs.get(hash) != null && loaderDialogs.get(hash).isShowing()) {
//            loaderDialogs.get(hash).dismiss();
//            loaderDialogs.remove(hash);
//        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getmViewModeHelper().performBinding(this);
        binding = getBinding();
        viewModel = getViewModel();
    }


    public void addFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(com.ranok.R.id.container, fragment, fragment.getTag())
                .addToBackStack(null)
                .commit();
    }

    public void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(com.ranok.R.id.container, fragment, fragment.getTag())
                .commit();
    }

    public void activateToolbarNoDrawer(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override
    public void showSnakeBar(String s) {
        hideKeyboard();
        Snackbar snackbar = Snackbar
                    .make(getBinding().getRoot(), s, Snackbar.LENGTH_LONG);
            snackbar.show();
    }

    @Override
    public void gotoLogin() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public void showSnakeBar(int i) {
        showSnakeBar(getString(i));
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @SuppressWarnings("unused")
    public B getBinding() {
        try {
            return (B) getmViewModeHelper().getBinding();
        } catch (ClassCastException ex) {
            throw new IllegalStateException("Method getViewModelBindingConfig() has to return same " +
                    "ViewDataBinding type as it is set to base Fragment");
        }
    }



}