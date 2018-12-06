package com.ranok.ui.reciept;

import com.ranok.network.models.RecieptListModel;
import com.ranok.ui.base.BaseIView;


public interface RecieptChooseOrderIView extends BaseIView {
    void showProcessing(RecieptListModel position);
    void startScanBarcode();
}
