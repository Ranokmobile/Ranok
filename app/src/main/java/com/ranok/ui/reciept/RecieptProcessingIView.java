package com.ranok.ui.reciept;

import com.ranok.ui.base.BaseIView;


public interface RecieptProcessingIView extends BaseIView {
    void showLot(int type, String lot, String position);
    void changeSpinner();
}
