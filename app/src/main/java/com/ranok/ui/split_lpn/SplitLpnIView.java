package com.ranok.ui.split_lpn;

import com.ranok.network.request.SplitLpnRequest;
import com.ranok.ui.base.BaseIView;


public interface SplitLpnIView extends BaseIView {
    void closeScreen();
    void showMoveFragment(SplitLpnRequest splitLpnRequest);
}
