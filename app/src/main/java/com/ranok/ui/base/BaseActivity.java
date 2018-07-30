package com.ranok.ui.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;

import com.ranok.ui.dialogs.LoaderDialog;

import ranok.mvvm.base.ViewModelBaseActivity;


public abstract class BaseActivity<T extends BaseIView, R extends BaseViewModel<T>, B extends ViewDataBinding>
        extends ViewModelBaseActivity<T, R> implements BaseIView {

    protected B binding;
    protected R viewModel;

    protected SparseArray<LoaderDialog> loaderDialogs = new SparseArray<>();

    @Override
    public void showLoader(int hashCode) {
        loaderDialogs.put(hashCode, new LoaderDialog(this));
        loaderDialogs.get(hashCode).show();
    }

    @Override
    public void hideLoader(int hash) {
        if (loaderDialogs.get(hash) != null && loaderDialogs.get(hash).isShowing()) {
            loaderDialogs.get(hash).dismiss();
            loaderDialogs.remove(hash);
        }
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
        Snackbar snackbar = Snackbar
                    .make(getBinding().getRoot(), s, Snackbar.LENGTH_LONG);
            snackbar.show();
    }

    @Override
    public void showSnakeBar(int i) {
        showSnakeBar(getString(i));
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