package com.ranok.ui.base;


import ranok.mvvm.IView;

public interface BaseIView extends IView {
    void showLoader(int hash);
    void hideLoader(int hash);
    void showSnakeBar(String s);
    void showSnakeBar(int i);
}
