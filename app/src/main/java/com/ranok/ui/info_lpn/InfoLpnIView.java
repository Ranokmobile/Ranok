package com.ranok.ui.info_lpn;

import com.ranok.network.models.LpnPositionModel;
import com.ranok.ui.base.BaseIView;

import java.util.ArrayList;


public interface InfoLpnIView extends BaseIView {
    void startScanBarcode();
    void showMove(String lpn);
    void showSplit(String lpn, ArrayList<LpnPositionModel> positions);
}
