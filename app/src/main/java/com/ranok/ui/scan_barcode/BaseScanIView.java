package com.ranok.ui.scan_barcode;

import com.ranok.ui.base.BaseIView;


public interface BaseScanIView extends BaseIView {
    void exit();
    void pauseScan();
    void resumeScan();
}
