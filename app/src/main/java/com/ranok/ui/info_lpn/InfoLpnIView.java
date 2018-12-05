package com.ranok.ui.info_lpn;

import com.ranok.network.models.PlaceInfoModel;
import com.ranok.ui.base.BaseIView;

import java.util.ArrayList;


public interface InfoLpnIView extends BaseIView {
    void startScanBarcode();
    void showMove(String lpn);
    void showSplit(String lpn, ArrayList<PlaceInfoModel> positions);
    void showUnpack(String lpn, ArrayList<PlaceInfoModel> positions);
    void showPrint(String lpn, ArrayList<PlaceInfoModel> positions);
    void refreshFab();
    void showMenu(PlaceInfoModel item);
}
