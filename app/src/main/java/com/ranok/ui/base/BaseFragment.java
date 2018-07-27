package com.ranok.ui.base;

import android.content.Context;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.TransitionManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ranok.mvvm.base.ViewModelBaseFragment;


public abstract class BaseFragment<T extends BaseIView, R extends BaseViewModel<T>, B extends ViewDataBinding>
        extends ViewModelBaseFragment<T, R> implements BaseIView {

    protected BaseActivity mActivity;
    protected Toolbar toolbar;
    private ViewDataBinding binding;
    protected boolean isWithBackOption = false, allowAnimation = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getViewModelHelper().performBinding(this);
        binding = getViewModelHelper().getBinding();

        if (binding != null ) {
            if (allowAnimation) {
                getBinding().addOnRebindCallback(new OnRebindCallback() {
                    @Override
                    public boolean onPreBind(ViewDataBinding binding) {
                        TransitionManager.beginDelayedTransition(
                                (ViewGroup) binding.getRoot());

                        return super.onPreBind(binding);
                    }
                });
            }
            return binding.getRoot();
        } else {
            throw new IllegalStateException("Binding cannot be null. Perform binding before calling getBinding()");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findToolbar();
    }

    @Override
    public void onResume() {
        super.onResume();
        notifyToolBarName();
    }

    protected void notifyToolBarName() {
        if (mActivity.getSupportActionBar() != null) {
            if (getScreenTitle() != null && toolbar != null) {
                toolbar.setTitle(getScreenTitle());
                mActivity.getSupportActionBar().setTitle(getScreenTitle());
            }
        }
    }

    protected String getScreenTitle() {
        return null;
    }


    @SuppressWarnings("unused")
    public B getBinding() {
        try {
            return (B) getViewModelHelper().getBinding();
        } catch (ClassCastException ex) {
            throw new IllegalStateException("Method getViewModelBindingConfig() has to return same " +
                    "ViewDataBinding type as it is set to base Fragment");
        }
    }

    public void findToolbar() {
        View view = getBinding().getRoot().findViewById(com.ranok.R.id.toolbarPanel);

        if (view != null) {
            toolbar = (Toolbar) view;
            mActivity.activateToolbarNoDrawer (toolbar);
        }
    }
}