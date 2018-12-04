package com.ranok.ui.pack_lpn;

import com.ranok.network.request.PackToLpnRequest;
import com.ranok.ui.base.BaseIView;


public interface PackLpnIView extends BaseIView {
    void closeScreen();
    void showMoveFragment(PackToLpnRequest packToLpnRequest);
}
