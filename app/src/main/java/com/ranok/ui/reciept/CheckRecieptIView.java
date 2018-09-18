package com.ranok.ui.reciept;

import com.ranok.ui.base.BaseIView;


public interface CheckRecieptIView extends BaseIView {
    void startScanBarcode();
    void showPlacementDialog(String lpn);
    void setupSpinner();
}
