package com.ranok.ui.info_place;

import com.ranok.network.models.PlaceInfoModel;
import com.ranok.ui.base.BaseIView;


public interface InfoPlaceIView extends BaseIView {
    void startScanBarcode();
    void showMenu(PlaceInfoModel item);
}
