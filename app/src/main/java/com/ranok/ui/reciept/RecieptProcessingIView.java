package com.ranok.ui.reciept;

import com.ranok.network.models.RecieptListModel;
import com.ranok.ui.base.BaseIView;


public interface RecieptProcessingIView extends BaseIView {
    void showLot(int type, String lot, RecieptListModel position);
    void changeSpinner();
}
