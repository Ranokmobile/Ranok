package com.ranok.ui.main_menu;


import com.ranok.ui.base.BaseIView;

interface MainMenuIView extends BaseIView {
    void showRFIDScan();
    void startLoginActivity();
    void showInfoPosition();
    void showInfoLpn();
    void showInfoPlace();
    void reciept();
    void checkReciept();
}
