package com.ranok.ui.move_lpn;

import com.ranok.ui.base.BaseIView;


public interface MoveLpnIView extends BaseIView {
    void startScanBarcode(MoveLpnVM.SearchWidgets client);
    void closeScreen();
    void showInputPlace(String header);
}
